package com.chargebee.common.UI;

import com.chargebee.*;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.By;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class LoginPage {
    public static final Target USERNAME =
            Target.the("user name filed in login").located(By.id("email"));
    public static final Target PASSWORD =
            Target.the("password filed in login").located(By.id("password"));
    public static final Target SIGNIN_BUTTON =
            Target.the("password filed in login").located(By.id("sign-in-submit"));


    public static Performable login() {
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
