package com.cbee.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.By;


@DefaultUrl("https://neha-singla-pc2-test.integrations.chargebee.com/quickbooks")
public class QBSyncPage extends PageObject {
    public static final Target SYNC_NOW = Target.the("Sync now").located(By.id("cb-integ-sync-now"));

}
