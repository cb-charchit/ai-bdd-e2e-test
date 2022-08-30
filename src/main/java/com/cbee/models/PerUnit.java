package com.cbee.models;

public class PerUnit extends PriceModel {
    public final Money unitPrice;

    public PerUnit(Money unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public Money usagePriceFor(int usageQuantity) {
        return unitPrice;
    }
}
