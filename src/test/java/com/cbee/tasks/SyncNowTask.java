package com.cbee.tasks;

import com.cbee.pages.QBSyncPage;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;

public class SyncNowTask {
    public Performable runSyncJob()
    {
        return Task.where("{0} run sync job", actor -> {
            actor.attemptsTo(Open.browserOn().the(QBSyncPage.class),
                    LoginTask.login(),
                    new SyncTask().sync());
            });
        }
    }
