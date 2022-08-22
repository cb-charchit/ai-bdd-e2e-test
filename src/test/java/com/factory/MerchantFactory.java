package com.factory;

import com.chargebee.common.models.Merchant;
import com.chargebee.common.models.User;
import org.openqa.selenium.InvalidArgumentException;

import java.util.ArrayList;

public class MerchantFactory {

    public static Merchant getMerchant(String merchantName) {
        String domainSuffix = System.getProperty("com.chargebee.api.domain.suffix");
        if ("adminBot".equals(merchantName)) {
            return adminBot(domainSuffix);
        }

        throw new InvalidArgumentException(merchantName + " not found");
    }

    private static Merchant adminBot(String domainSuffix) {
        {
            User user = new User("Neha", "Singla", "neha.singla@chargebee.com", "Neha@1121");
            ArrayList<User> users = new ArrayList<>();
            users.add(user);
            return new Merchant("adminBot", "neha-singla-test" + domainSuffix);
        }
    }
}
