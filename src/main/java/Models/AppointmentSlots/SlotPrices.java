package Models.AppointmentSlots;

import java.math.BigDecimal;

public class SlotPrices {
    public int slotSize;
    public BigDecimal nurseCost;
    public BigDecimal doctorCost;
    public int maxSlotSize;

    public SlotPrices(int slotSize, BigDecimal nurseCost, BigDecimal doctorCost, int maxSlotSize) {
        this.slotSize = slotSize;
        this.nurseCost = nurseCost;
        this.doctorCost = doctorCost;
        this.maxSlotSize = maxSlotSize;
    }

    public int getSlotSize() {
        return slotSize;
    }

    public BigDecimal getNurseCost() {
        return nurseCost;
    }

    public BigDecimal getDoctorCost() {
        return doctorCost;
    }

    public int getMaxSlotSize() {
        return maxSlotSize;
    }
}
