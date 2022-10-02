package com.cbee.pages;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;


@DefaultUrl("https://neha-singla-pc2-test.integrations.chargebee.com/quickbooks/edit_mapping")
public class ManageMappingPage extends PageObject {

    public static Target select_account_list(String gateway, String currency) {
        String comboboxItemXPathFormat = "//div[@data-iteg-grp-name='%s']//div[@id='%s']";
        return Target.the("Account List").located(By.xpath(String.format(comboboxItemXPathFormat, gateway, currency + "_id")));
    }


    public static Target select_account(String gateway, String account) {
        String comboboxItemXPathFormat = "//div[@data-iteg-grp-name='%s']//span[text()='%s']";
        return Target.the("Account").located(By.xpath(String.format(comboboxItemXPathFormat, gateway, account)));
    }

    public static final Target SAVE_BUTTON = Target.the("Save").located(By.xpath("//span[contains(text(),'Save')]"));
}
