package com.chargebee.common.features;

import com.chargebee.common.UI.LoginPage;
import com.chargebee.qbo.UI.QBSyncPage;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;

public class Sync {

    public static Performable runSyncJob()
    {
        return Task.where("{0} run sync job", actor -> {
            actor.attemptsTo(Open.browserOn().the(QBSyncPage.class),
                    LoginPage.login(),
                    QBSyncPage.sync());
            });
        }
    }
