package com.cbee.tasks;

import com.cbee.models.User;
import com.cbee.pages.LoginPage;
import com.cbee.utils.ConfigFileReader;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class LoginTask {

    public static Performable login() {

        return Task.where("{0} attempts to login", actor -> {
            actor.attemptsTo(
                    WaitUntil.the(LoginPage.USERNAME, isVisible()).forNoMoreThan(12000).seconds(),
                    Enter.theValue(new ConfigFileReader().getConfigValueByKey("siteUserName")).into(LoginPage.USERNAME),
                    Enter.theValue(new ConfigFileReader().getConfigValueByKey("sitePassword")).into(LoginPage.PASSWORD),
                    Click.on(LoginPage.SIGNIN_BUTTON)
            );
        });
    }
}
