package com.cbee.tasks;

import com.cbee.pages.QBSyncPage;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static com.cbee.pages.QBSyncPage.UNLINK_CONFIRMATION;
import static com.cbee.pages.QBSyncPage.UNLINK_INTEGRATION;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;

public class UnlinkIntegrationTask {

    public Performable unlinkIntegration() {
        Performable unlinkInteg =
                Task.where("{0} Unlink integration via UI", actor -> {
                    actor.attemptsTo(Open.browserOn().the(QBSyncPage.class),
                            LoginTask.login()
                    );
                });

        unlinkInteg = unlinkInteg.then(Task.where("", actor -> {
            actor.attemptsTo(
                    WaitUntil.the(UNLINK_INTEGRATION, isClickable()).forNoMoreThan(40).seconds(),
                    Click.on(UNLINK_INTEGRATION),
                    Click.on(UNLINK_INTEGRATION),
                    WaitUntil.the(UNLINK_CONFIRMATION, isClickable()).forNoMoreThan(40).seconds(),
                    Click.on(UNLINK_CONFIRMATION)
            );
        }));

        return unlinkInteg;
    }
}
