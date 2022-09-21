package com.cbee.factory;

import com.cbee.models.*;
import com.cbee.utils.ConfigFileReader;
import org.openqa.selenium.InvalidArgumentException;

import java.util.ArrayList;
import java.util.HashMap;

public class MerchantFactory {

    public static Merchant getMerchant(String merchantName) {
        String domainSuffix = System.getProperty("chargebee.com");
        if ("Rajat".equals(merchantName)) {
            return Rajat(domainSuffix);
        }

        throw new InvalidArgumentException(merchantName + " not found");
    }

    private static Merchant Rajat(String domainSuffix) {
        {
            ItemFamily bddFamily = new ItemFamily("bdd_family");
            HashMap<String, PricePoint> pricePoints = new HashMap<>();

            Plan flat_fee_plan = new Plan("flat_fee_plan", "flat_fee_plan", bddFamily);
            PricePoint usdMonthly = new PricePoint("flat_fee_plan-USD-Monthly", flat_fee_plan, new FlatFee(new Money( Currency.USD,100)), BillingFrequency.MONTHLY);
            pricePoints.put(usdMonthly.id, usdMonthly);

            Addon ffRecurringAddon = new Addon("ff_recurring_addon","ff_recurring_addon",bddFamily);
            PricePoint recurringAddon = new PricePoint("ff_recurring_addon-USD-Monthly", ffRecurringAddon, new FlatFee(new Money( Currency.USD,10)), BillingFrequency.MONTHLY);
            pricePoints.put(recurringAddon.id, recurringAddon);

            Addon ffNonRecurringAddon = new Addon("ff_non_recurring_addon","ff_non_recurring_addon",bddFamily);
            PricePoint nonRecurringAddon = new PricePoint("ff_non_recurring_addon-USD-Monthly", ffNonRecurringAddon, new FlatFee(new Money( Currency.USD,10)), BillingFrequency.MONTHLY);
            pricePoints.put(nonRecurringAddon.id, nonRecurringAddon);

            Plan per_unit_plan = new Plan("per_unit_plan", "per_unit_plan", bddFamily);
            PricePoint usdMonthlyPerUnit = new PricePoint("per_unit_plan-USD-Monthly", per_unit_plan, new PerUnit(new Money( Currency.USD,100)), BillingFrequency.MONTHLY);
            pricePoints.put(usdMonthlyPerUnit.id, usdMonthlyPerUnit);

            ArrayList<PricingSlab> pricingSlabs = new ArrayList<>();
            pricingSlabs.add(new PricingSlab(1, 100, 20));
            pricingSlabs.add(new PricingSlab(101, 200, 10));
            pricingSlabs.add(new PricingSlab(201,  5));
            TieredPriceModel tieredPriceModel = new TieredPriceModel(Currency.USD, pricingSlabs);

            MeteredPlan tieredPlan = new MeteredPlan(new Plan("Tiered-Plan", "Tiered-Plan", bddFamily), MeteredBillingMode.SUM_OF_ALL_USAGES);
            PricePoint tieredPlanUSDMonthly = new PricePoint("Tiered-Plan-USD-Monthly", tieredPlan, tieredPriceModel, BillingFrequency.MONTHLY);
            tieredPlanUSDMonthly.setQuantity(250f);
            pricePoints.put(tieredPlanUSDMonthly.id, tieredPlanUSDMonthly);

            MeteredPlan stairStepPlan = new MeteredPlan(new Plan("StairStep-Plan", "StairStep-Plan", bddFamily), MeteredBillingMode.SUM_OF_ALL_USAGES);
            PricePoint stairStepPlanUSDMonthly = new PricePoint("StairStep-Plan-USD-Monthly", stairStepPlan, tieredPriceModel, BillingFrequency.MONTHLY);
            stairStepPlanUSDMonthly.setQuantity(250f);
            pricePoints.put(stairStepPlanUSDMonthly.id, stairStepPlanUSDMonthly);

            MeteredPlan volumePlan = new MeteredPlan(new Plan("Volume-Plan", "Volume-Plan", bddFamily), MeteredBillingMode.SUM_OF_ALL_USAGES);
            PricePoint volumePlanUSDMonthly = new PricePoint("Volume-Plan-USD-Monthly", volumePlan, tieredPriceModel, BillingFrequency.MONTHLY);
            volumePlanUSDMonthly.setQuantity(250f);
            pricePoints.put(volumePlanUSDMonthly.id, volumePlanUSDMonthly);

            ProductCatalog productCatalog = new ProductCatalog(pricePoints);

            ConfigFileReader config = new ConfigFileReader();
            User user = new User();
            user.setEmail(config.getSiteUserName());
            user.setPassword(config.getSitePassword());

            ArrayList<User> users = new ArrayList<>();
            users.add(user);
            return new Merchant("Rajat",
                    new Site(domainSuffix, config.getConfigValueByKey("siteName"), config.getConfigValueByKey("prod.siteApiKey"), productCatalog, users));
        }
    }
}
