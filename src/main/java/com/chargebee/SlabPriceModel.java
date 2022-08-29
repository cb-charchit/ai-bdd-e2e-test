package com.chargebee;

import java.util.List;

public abstract class SlabPriceModel extends PriceModel {
    public final Currency currency;
    public final List<PricingSlab> pricingSlabs;

    protected SlabPriceModel(Currency currency, List<PricingSlab> pricingSlabs) {
        this.currency = currency;
        this.pricingSlabs = pricingSlabs;
    }
}
