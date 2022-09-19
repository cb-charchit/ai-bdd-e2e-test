package com.cbee.factory;

import com.cbee.models.*;
import com.cbee.utils.ConfigFileReader;
import org.openqa.selenium.InvalidArgumentException;

import java.io.IOException;
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
