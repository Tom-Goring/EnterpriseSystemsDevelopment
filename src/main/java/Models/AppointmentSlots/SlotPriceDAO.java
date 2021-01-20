package Models.AppointmentSlots;

import Utils.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SlotPriceDAO {
    public static void updateSlotPrices(SlotPrices newSlotPrices) {
        Connection con = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE SLOTPRICES SET SLOTINTERVAL = ?, SLOTCOST = ?, SLOTMAXIMUMDURATION = ? WHERE ID = 1");
            ps.setInt(1, newSlotPrices.slotSize);
            ps.setBigDecimal(2, newSlotPrices.slotCost);
            ps.setInt(3, newSlotPrices.maxSlotSize);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static SlotPrices getCurrentSlotPrices() {
        Connection con = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM SLOTPRICES");
            ResultSet rs = ps.executeQuery();
            rs.next();

            // start at 2 to ignore the ID
            return new SlotPrices(
                    rs.getInt(2),
                    rs.getBigDecimal(3),
                    rs.getInt(4)
            );

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
}