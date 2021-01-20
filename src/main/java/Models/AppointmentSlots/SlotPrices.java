package Models.AppointmentSlots;

import java.math.BigDecimal;

public class SlotPrices {
    public int slotSize;
    public BigDecimal slotCost;
    public int maxSlotSize;

    public SlotPrices(int slotSize, BigDecimal slotCost, int maxSlotSize) {
        this.slotSize = slotSize;
        this.slotCost = slotCost;
        this.maxSlotSize = maxSlotSize;
    }

    public int getSlotSize() {
        return slotSize;
    }

    public BigDecimal getSlotCost() {
        return slotCost;
    }

    public int getMaxSlotSize() {
        return maxSlotSize;
    }
}
