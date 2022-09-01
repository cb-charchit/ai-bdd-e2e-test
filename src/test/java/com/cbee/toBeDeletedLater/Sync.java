package com.cbee.toBeDeletedLater;

import com.cbee.pages.LoginPage;
import com.cbee.pages.QBSyncPage;
import com.cbee.tasks.LoginTask;
import com.cbee.tasks.SyncTask;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.questions.page.TheWebPage;
import net.thucydides.core.annotations.Managed;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.containsString;

@RunWith(SerenityRunner.class)
public class Sync {

    @Managed
    WebDriver browser;

    Actor user = Actor.named("User");

    @Before
    public void setTheStage() {
        user.can(BrowseTheWeb.with(browser));
        user.attemptsTo(
                Open.browserOn().the(QBSyncPage.class)
        );
    }

    @Test
    public void when_running_sync_now_job() {

        user.should(seeThat(TheWebPage.title(), containsString("User Login - Chargebee")));

        user.attemptsTo(new LoginTask().login(),
                new SyncTask().sync()
        );

    }
}
