package com.cbee.pages;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.screenplay.targets.Target;


public class LoginPage {
    public static final Target USERNAME =
            Target.the("user name filed in login").located(By.id("email"));
    public static final Target PASSWORD =
            Target.the("password filed in login").located(By.id("password"));
    public static final Target SIGNIN_BUTTON =
            Target.the("password filed in login").located(By.id("sign-in-submit"));

}
