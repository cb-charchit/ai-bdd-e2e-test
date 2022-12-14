package com.cbee.toBeDeletedLater;

import com.cbee.pages.ManageMappingPage;
import com.cbee.tasks.LoginTask;
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

import static com.cbee.tasks.MapGLAccountTask.save;
import static com.cbee.tasks.MapGLAccountTask.selectGatewayAccountMapping;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.containsString;

@RunWith(SerenityRunner.class)
public class SaveManageMappingConfig {

    @Managed
    WebDriver browser;

    Actor admin = Actor.named("Admin");

    @Before
    public void setTheStage() {
        admin.can(BrowseTheWeb.with(browser));
        admin.attemptsTo(
                Open.browserOn().the(ManageMappingPage.class)
        );
    }

    @Test
    public void when_saving_manage_mapping_config() {

        admin.should(seeThat(TheWebPage.title(), containsString("User Login - Chargebee")));

        admin.attemptsTo(

                LoginTask.login(),
                selectGatewayAccountMapping(null),
                save()
        );
    }

}
