package com.cbee.tasks;

import com.cbee.pages.QBSyncPage;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;

public class SyncTask {

    public Performable sync() {
        return Task.where("{0} attempts to sync entities", actor -> {
            actor.attemptsTo(
                    WaitUntil.the(QBSyncPage.SYNC_NOW, isClickable()).forNoMoreThan(36000).seconds(),
                    Click.on(QBSyncPage.SYNC_NOW),
                    WaitUntil.the(QBSyncPage.SYNC_NOW, isClickable()).forNoMoreThan(36000).seconds()
            );
        });
    }
}
