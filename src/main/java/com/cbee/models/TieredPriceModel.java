package com.cbee.models;

import java.math.BigDecimal;
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

        BigDecimal sum = new BigDecimal(0);
        for (PricingSlab pricingSlab :  pricingSlabs) {
            if (usageQuantity >= pricingSlab.from) {
                if (pricingSlab.to.isPresent() && usageQuantity>=pricingSlab.to.get()) {
                   BigDecimal b= new BigDecimal(pricingSlab.to.get()-pricingSlab.from+1);
                    BigDecimal b2 = (pricingSlab.moneyValue).multiply(b);
                     sum =sum.add(b2);
                    // TODO: do the pricing calculation logic
                }else {
                    BigDecimal r = (pricingSlab.moneyValue).multiply(new BigDecimal(usageQuantity - pricingSlab.from+1));
                    sum=sum.add(r);
                }
            }
        }
        Money usagePrice = new Money(currency, sum);
        return usagePrice;
    }
}


// 1 -100 checking 250>100  100-1+1*money
// 101-200 checking          200-101+1*money
// 201-above if to is not present  250-201+1*money