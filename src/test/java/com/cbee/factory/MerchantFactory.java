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
            ItemFamily bddFamily = new ItemFamily("bdd-family");
            HashMap<String, PricePoint> pricePoints = new HashMap<>();

            Plan flatFeePlan = new Plan("FlatFee-Plan", "FlatFee-Plan", bddFamily);
            PricePoint flatFeePlanUsdMonthly = new PricePoint("FlatFee-Plan-USD-Monthly", flatFeePlan, new FlatFee(new Money( Currency.USD,100)), BillingFrequency.MONTHLY);
            pricePoints.put(flatFeePlanUsdMonthly.id, flatFeePlanUsdMonthly);

            Addon flatFeeAddon = new Addon("FlatFee-Addon","FlatFee-Addon",bddFamily);
            PricePoint flatFeeAddonUsdMonthly = new PricePoint("FlatFee-Addon-USD-Monthly", flatFeeAddon, new FlatFee(new Money( Currency.USD,10)), BillingFrequency.MONTHLY);
            pricePoints.put(flatFeeAddonUsdMonthly.id, flatFeeAddonUsdMonthly);

            Charge flatFeeCharge = new Charge("FlatFee-Charge","FlatFee-Charge",bddFamily);
            PricePoint flatFeeChargeUsdMonthly = new PricePoint("FlatFee-Charge-USD-Monthly", flatFeeCharge, new FlatFee(new Money( Currency.USD,10)), BillingFrequency.MONTHLY);
            pricePoints.put(flatFeeChargeUsdMonthly.id, flatFeeChargeUsdMonthly);

            Plan perUnitPlan = new Plan("PerUnit-Plan", "PerUnit-Plan", bddFamily);
            PricePoint perUnitPlanUsdMonthly = new PricePoint("PerUnit-Plan-USD-Monthly", perUnitPlan, new PerUnit(new Money( Currency.USD,100)), BillingFrequency.MONTHLY);
            pricePoints.put(perUnitPlanUsdMonthly.id, perUnitPlanUsdMonthly);

            Addon perUnitAddon = new Addon("PerUnit-Addon","PerUnit-Addon",bddFamily);
            PricePoint perUnitAddonUsdMonthly = new PricePoint("PerUnit-Addon-USD-Monthly", perUnitAddon, new PerUnit(new Money( Currency.USD,10)), BillingFrequency.MONTHLY);
            pricePoints.put(perUnitAddonUsdMonthly.id, perUnitAddonUsdMonthly);

            Charge perUnitCharge = new Charge("PerUnit-Charge","PerUnit-Charge",bddFamily);
            PricePoint perUnitChargeUsdMonthly = new PricePoint("PerUnit-Charge-USD-Monthly", perUnitCharge, new PerUnit(new Money( Currency.USD,10)), BillingFrequency.MONTHLY);
            pricePoints.put(perUnitChargeUsdMonthly.id, perUnitChargeUsdMonthly);

            ArrayList<PricingSlab> pricingSlabs = new ArrayList<>();
            pricingSlabs.add(new PricingSlab(1, 100, 20));
            pricingSlabs.add(new PricingSlab(101, 200, 10));
            pricingSlabs.add(new PricingSlab(201,  5));

            TieredPriceModel tieredPriceModel = new TieredPriceModel(Currency.USD, pricingSlabs);

            MeteredPlan tieredPlan = new MeteredPlan(new Plan("Tiered-Plan", "Tiered-Plan", bddFamily), MeteredBillingMode.SUM_OF_ALL_USAGES);
            PricePoint tieredPlanUsdMonthly = new PricePoint("Tiered-Plan-USD-Monthly", tieredPlan, tieredPriceModel, BillingFrequency.MONTHLY);
            pricePoints.put(tieredPlanUsdMonthly.id, tieredPlanUsdMonthly);

            Addon tieredAddon = new Addon("Tiered-Addon","Tiered-Addon",bddFamily);
            PricePoint tieredAddonUsdMonthly = new PricePoint("Tiered-Addon-USD-Monthly", tieredAddon, tieredPriceModel, BillingFrequency.MONTHLY);
            pricePoints.put(tieredAddonUsdMonthly.id, tieredAddonUsdMonthly);

            Charge tieredCharge = new Charge("Tiered-Charge","Tiered-Charge",bddFamily);
            PricePoint tieredChargeUsdMonthly = new PricePoint("Tiered-Charge-USD-Monthly", tieredCharge, tieredPriceModel, BillingFrequency.MONTHLY);
            pricePoints.put(tieredChargeUsdMonthly.id, tieredChargeUsdMonthly);


            MeteredPlan stairStepPlan = new MeteredPlan(new Plan("StairStep-Plan", "StairStep-Plan", bddFamily), MeteredBillingMode.SUM_OF_ALL_USAGES);
            PricePoint stairStepPlanUsdMonthly = new PricePoint("StairStep-Plan-USD-Monthly", stairStepPlan, tieredPriceModel, BillingFrequency.MONTHLY);
            pricePoints.put(stairStepPlanUsdMonthly.id, stairStepPlanUsdMonthly);

            Addon stairStepAddon = new Addon("StairStep-Addon","StairStep-Addon",bddFamily);
            PricePoint stairStepAddonUsdMonthly = new PricePoint("StairStep-Addon-USD-Monthly", stairStepAddon, tieredPriceModel, BillingFrequency.MONTHLY);
            pricePoints.put(stairStepAddonUsdMonthly.id, stairStepAddonUsdMonthly);

            Charge stairStepCharge = new Charge("StairStep-Charge","StairStep-Charge",bddFamily);
            PricePoint stairStepChargeUsdMonthly = new PricePoint("StairStep-Charge-USD-Monthly", stairStepCharge, tieredPriceModel, BillingFrequency.MONTHLY);
            pricePoints.put(stairStepChargeUsdMonthly.id, stairStepChargeUsdMonthly);

            MeteredPlan volumePlan = new MeteredPlan(new Plan("Volume-Plan", "Volume-Plan", bddFamily), MeteredBillingMode.SUM_OF_ALL_USAGES);
            PricePoint volumePlanUsdMonthly = new PricePoint("Volume-Plan-USD-Monthly", volumePlan, tieredPriceModel, BillingFrequency.MONTHLY);
            pricePoints.put(volumePlanUsdMonthly.id, volumePlanUsdMonthly);

            Addon volumeAddon = new Addon("Volume-Addon","Volume-Addon",bddFamily);
            PricePoint volumeAddonUsdMonthly = new PricePoint("Volume-Addon-USD-Monthly", volumeAddon, tieredPriceModel, BillingFrequency.MONTHLY);
            pricePoints.put(volumeAddonUsdMonthly.id, volumeAddonUsdMonthly);

            Charge volumeCharge = new Charge("Volume-Charge","Volume-Charge",bddFamily);
            PricePoint volumeChargeUsdMonthly = new PricePoint("Volume-Charge-USD-Monthly", volumeCharge, tieredPriceModel, BillingFrequency.MONTHLY);
            pricePoints.put(volumeChargeUsdMonthly.id, volumeChargeUsdMonthly);

            ProductCatalog productCatalog = new ProductCatalog(pricePoints);

            ConfigFileReader config = new ConfigFileReader();
            User user = new User();
            user.setEmail(config.getSiteUserName());
            user.setPassword(config.getSitePassword());

            ArrayList<User> users = new ArrayList<>();
            users.add(user);
            return new Merchant("Rajat",
                    new Site(domainSuffix, config.getConfigValueByKey("siteName"), config.getConfigValueByKey("siteApiKey"), productCatalog, users));
        }
    }
}
