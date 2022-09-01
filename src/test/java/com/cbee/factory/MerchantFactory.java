package com.cbee.factory;

import com.cbee.models.*;
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
            ItemFamily e2ePocFamily = new ItemFamily("e2e_poc_family");
            HashMap<String, PricePoint> pricePoints = new HashMap<>();

            Plan e2e_poc_plan_monthly = new Plan("e2e_poc_plan", "e2e_poc_plan", e2ePocFamily);
            PricePoint usdMonthly = new PricePoint("e2e_poc_plan-USD-Monthly", e2e_poc_plan_monthly, new FlatFee(new Money( Currency.USD,10)), BillingFrequency.MONTHLY);
            pricePoints.put(usdMonthly.id, usdMonthly);

            ProductCatalog productCatalog = new ProductCatalog(pricePoints);

            User user = new User("Neha", "Singla", "neha.singla+pc2@chargebee.com", "Neha@1121");
            ArrayList<User> users = new ArrayList<>();
            users.add(user);
            return new Merchant("Rajat",
                    new Site(domainSuffix, "neha-singla-pc2-test", "test_Pt8zayBmpp7Buc6zacd3xcNyzqBNcd89UY", productCatalog, users));
        }
    }
}
