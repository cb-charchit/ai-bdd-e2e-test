package com.cbee.models;

public class FlatFee extends PriceModel {
    public final Money unitPrice;

    public FlatFee(Money unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public Money usagePriceFor(int usageQuantity) {
        return unitPrice;
    }
}
