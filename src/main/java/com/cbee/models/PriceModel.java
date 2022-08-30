package com.cbee.models;

public abstract class PriceModel {
    public Money price() {
        return usagePriceFor(0);
    }

    public abstract Money usagePriceFor(int usageQuantity);
}
