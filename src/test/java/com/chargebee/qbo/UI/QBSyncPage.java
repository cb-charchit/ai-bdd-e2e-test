package com.chargebee.qbo.UI;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.By;

@DefaultUrl("https://neha-singla-test.integrations.devcb.in/quickbooks")
public class QBSyncPage extends PageObject {
    public static final Target SYNC_NOW =
            Target.the("Sync now").located(By.id("cb-integ-sync-now"));

    public static final Target SYNC_DETAILS =
            Target.the("Sync Details").located(By.id("sync-details"));

    public static Question<String> sync_details() {
        return (Question<String>) Target.the("Sync Details").located(By.id("sync-details"));
    }
}
