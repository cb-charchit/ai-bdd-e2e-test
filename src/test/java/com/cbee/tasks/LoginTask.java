package com.cbee.tasks;

import com.cbee.models.User;
import com.cbee.pages.LoginPage;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class LoginTask {

    public Performable login() {
        User user = new User("Neha", "Singla", "neha.singla@chargebee.com", "Neha@1121");

        return Task.where("{0} attempts to login", actor -> {
            actor.attemptsTo(
                    WaitUntil.the(LoginPage.USERNAME, isVisible()).forNoMoreThan(20).seconds(),
                    Enter.theValue(user.getEmail()).into(LoginPage.USERNAME),
                    Enter.theValue(user.getPassword()).into(LoginPage.PASSWORD),
                    Click.on(LoginPage.SIGNIN_BUTTON)
            );
        });
    }
}
