package com.cbee.pages;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;


@DefaultUrl("https://neha-singla-pc2-test.integrations.chargebee.com/quickbooks/edit_mapping")
public class ManageMappingPage extends PageObject {

    public static Target selectAccountListForPayments(String gateway, String currency) {
        String comboboxItemXPathFormat = "//div[@data-iteg-grp-name='%s']//div[@id='%s']";
        return Target.the("GL Account List for payment gateways").located(By.xpath(String.format(comboboxItemXPathFormat, gateway, currency + "_id")));
    }

    public static Target selectAccountForPayments(String gateway, String account) {
        String comboboxItemXPathFormat = "//div[@data-iteg-grp-name='%s']//span[text()='%s']";
        return Target.the("GL Account for payment gateways").located(By.xpath(String.format(comboboxItemXPathFormat, gateway, account)));
    }


    public static Target selectAccountListForLineItems(String id) {
        String comboboxItemXPathFormat = "//div[@id='%s']";
        return Target.the("GL Account List for Line Items").located(By.xpath(String.format(comboboxItemXPathFormat, id)));
    }

    public static Target selectAccountForLineItems(String id, String account) {
        String comboboxItemXPathFormat = "//div[@id='%s']//span[contains(text(),'%s')]";
        return Target.the("GL Account for Line Items").located(By.xpath(String.format(comboboxItemXPathFormat, id, account)));
    }


    public static Target selectTrackingCategoryClass(String id) {
        String comboboxItemXPathFormat = "//div[@data-iteg-grp-name='%s']/div[2]/div";
        return Target.the("Tracking Category Drop Down for Line Items").located(By.xpath(String.format(comboboxItemXPathFormat, id)));
    }

    public static Target selectTrackingCategoryClassField(String id) {
        String comboboxItemXPathFormat = "//div[@data-iteg-grp-name='%s']/div[2]/div/div[2]/div/div/div[1]/input";
        return Target.the("Tracking Category Fields").located(By.xpath(String.format(comboboxItemXPathFormat, id)));
    }

    public static Target selectTrackingCategoryClassFieldApplyButton(String id) {
        String comboboxItemXPathFormat = "//div[@data-iteg-grp-name='%s']//a[text()='Apply']";
        return Target.the("Tracking Category Apply button").located(By.xpath(String.format(comboboxItemXPathFormat, id)));
    }

    public static final Target SAVE_BUTTON = Target.the("Save").located(By.xpath("//span[contains(text(),'Save')]"));


    //discounts_id
    //bad_debts_id
    //round_off_id

    //div[@data-iteg-grp-name='round_off']//input[@name='tracking1']

    //div[@data-iteg-grp-name='stripe']//div[@id='usd']
}
