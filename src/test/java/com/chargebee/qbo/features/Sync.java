package com.chargebee.qbo.features;

import com.chargebee.common.UI.LoginPage;
import com.chargebee.common.models.User;
import com.chargebee.qbo.UI.QBSyncPage;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.questions.page.TheWebPage;
import net.serenitybdd.screenplay.waits.WaitUntil;
import net.thucydides.core.annotations.Managed;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static org.hamcrest.Matchers.containsString;

@RunWith(SerenityRunner.class)
public class Sync {

    @Managed
    WebDriver browser;

    Actor carrie = Actor.named("Carrie");

    @Before
    public void setTheStage() {
        carrie.can(BrowseTheWeb.with(browser));
        carrie.attemptsTo(
                Open.browserOn().the(QBSyncPage.class)
        );
    }

    @Test
    public void when_running_sync_now_job() {
        User user = new User("Neha", "Singla", "neha.singla@chargebee.com", "Neha@1121");
        carrie.should(
                seeThat(
                        TheWebPage.title(),
                        containsString("User Login - Chargebee")));

        carrie.attemptsTo(
                WaitUntil.the(LoginPage.USERNAME, isVisible()).forNoMoreThan(10).seconds(),
                Enter.theValue(user.email).into(LoginPage.USERNAME),
                Enter.theValue(user.password).into(LoginPage.PASSWORD),
                Click.on(LoginPage.SIGNIN_BUTTON),
                WaitUntil.the(QBSyncPage.SYNC_NOW, isClickable()).forNoMoreThan(20).seconds(),
                Click.on(QBSyncPage.SYNC_NOW),
                WaitUntil.the(QBSyncPage.SYNC_NOW, isClickable()).forNoMoreThan(40).seconds()
                );

    }
}
