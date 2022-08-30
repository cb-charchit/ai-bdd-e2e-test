package com.cbee.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.By;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;

@DefaultUrl("https://neha-singla-test.integrations.chargebee.com/quickbooks/edit_mapping")
public class ManageMappingPage extends PageObject {

    public static final Target ACCOUNT_DROPDOWN = Target.the("Account List").located(net.serenitybdd.core.annotations.findby.By.className("cn-menu__labela"));
    public static final Target SAVE_BUTTON = Target.the("Save").located(By.xpath("//span[contains(text(),'Save')]"));

    public static Target select_account(String acc_value) {
        String comboboxItemXPathFormat = "//span[contains(@value,'%s')]";
        return Target.the("Account").located(By.xpath(String.format(comboboxItemXPathFormat, acc_value)));
    }

    public static Performable saveAccountDetails() {
        return Task.where("{0} attempts to login", actor -> {
            actor.attemptsTo(
                    WaitUntil.the(ManageMappingPage.ACCOUNT_DROPDOWN, isClickable()).forNoMoreThan(40).seconds(),
                    Click.on(ManageMappingPage.ACCOUNT_DROPDOWN),
                    Click.on(ManageMappingPage.ACCOUNT_DROPDOWN),
                    WaitUntil.the(ManageMappingPage.select_account("36"), isClickable()).forNoMoreThan(40).seconds(),
                    Click.on(ManageMappingPage.select_account("36")),
                    WaitUntil.the(ManageMappingPage.SAVE_BUTTON, isClickable()).forNoMoreThan(40).seconds(),
                    Click.on(ManageMappingPage.SAVE_BUTTON),
                    WaitUntil.the(QBSyncPage.SYNC_NOW, isClickable()).forNoMoreThan(40).seconds()
            );
        });
    }

}
