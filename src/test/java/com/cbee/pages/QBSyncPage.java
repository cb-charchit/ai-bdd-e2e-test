package com.cbee.pages;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;


@DefaultUrl("https://neha-singla-pc2-test.integrations.chargebee.com/quickbooks")
public class QBSyncPage extends PageObject {
    public static final Target SYNC_NOW = Target.the("Sync now").located(By.id("cb-integ-sync-now"));

    public static final Target UNLINK_INTEGRATION =
            Target.the("unlink integration").located(By.id("integ-unlink-accounting-ui"));

    public static final Target UNLINK_CONFIRMATION=
            Target.the("unlink integration").located(By.xpath("//*[@id='integ_unlink_accounting']/div/div[2]/div[2]/div[2]/div[1]/input"));

}
