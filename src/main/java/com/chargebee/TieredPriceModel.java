package com.chargebee;

import java.util.List;

public class TieredPriceModel extends SlabPriceModel {
    public TieredPriceModel(Currency currency, List<PricingSlab> pricingSlabs) {
        super(currency, pricingSlabs);
    }

//    1002
    // 0 - 1000 => $1 = 1000
    // > 1000 => $2  = 4

    @Override
    public Money usagePriceFor(int usageQuantity) {
        Money usagePrice = new Money(currency, 0);
        for (PricingSlab pricingSlab :  pricingSlabs) {
            if (usageQuantity >= pricingSlab.from) {
                if (pricingSlab.to.isPresent()) {
                    // TODO: do the pricing calculation logic
                }
            }
        }
        return usagePrice;
    }
}
