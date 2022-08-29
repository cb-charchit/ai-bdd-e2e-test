package com.chargebee.qbo.UI;

import com.chargebee.common.UI.LoginPage;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.By;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

@DefaultUrl("https://neha-singla-pc2-test.integrations.chargebee.com/quickbooks")
public class QBSyncPage extends PageObject {
    public static final Target SYNC_NOW = Target.the("Sync now").located(By.id("cb-integ-sync-now"));

    public static Performable sync() {
        return Task.where("{0} attempts to login", actor -> {
            actor.attemptsTo(
                    WaitUntil.the(QBSyncPage.SYNC_NOW, isClickable()).forNoMoreThan(40).seconds(),
                    Click.on(QBSyncPage.SYNC_NOW),
                    WaitUntil.the(QBSyncPage.SYNC_NOW, isClickable()).forNoMoreThan(40).seconds()
            );
        });
    }
}
