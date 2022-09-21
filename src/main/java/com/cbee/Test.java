package com.cbee;

import com.cbee.models.PricingSlab;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Test {

    public static void main(String[] args) {
        BigDecimal m = usagePriceFor(300);
        System.out.println(m);
    }

    public static BigDecimal usagePriceFor(int usageQuantity) {
        ArrayList<PricingSlab> pricingSlabs = new ArrayList<>();
        pricingSlabs.add(new PricingSlab(1, 100, 20));
        pricingSlabs.add(new PricingSlab(101, 200, 10));
        pricingSlabs.add(new PricingSlab(201,  5));

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

        return sum;
    }
}
