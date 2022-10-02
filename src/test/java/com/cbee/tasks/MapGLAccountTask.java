package com.cbee.tasks;

import com.cbee.pages.ManageMappingPage;
import com.cbee.pages.QBSyncPage;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.waits.WaitUntil;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.cbee.pages.ManageMappingPage.select_account;
import static com.cbee.pages.ManageMappingPage.select_account_list;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;

public class MapGLAccountTask {

    public Performable mapGLAccountForPayments(Map<String, String> gatewayGLAccountMap) {
        return Task.where("{0} map GL Account for payments and refunds", actor -> {
            actor.attemptsTo(Open.browserOn().the(ManageMappingPage.class),
                    LoginTask.login(),
                    saveAccountMappingDetails(gatewayGLAccountMap));
        });
    }

    public static Performable saveAccountMappingDetails(Map<String, String> gatewayGLAccountMap) {

        return Task.where("{0} attempts to select and save account mapping details", (actor) -> {
            actor.attemptsTo(
                    mapAccounts(gatewayGLAccountMap),
                    save()
            );
        });
    }

    public static Performable mapAccounts(Map<String, String> gatewayGLAccountMap) {
        List<Performable> performableTaskList = dynamicGatewayAccountMapping(gatewayGLAccountMap);

        return Task.where("{0} attempts to select account", (actor) -> {
            for (Performable performableTask : performableTaskList) {
                actor.attemptsTo(performableTask);
            }
        });
    }

    public static List<Performable> dynamicGatewayAccountMapping(Map<String, String> gatewayGLAccountMap) {
        List<Performable> performableTaskList = new LinkedList<>();
        boolean flag = true;

        for (String gateway : gatewayGLAccountMap.keySet()) {
            Performable manageMapping =
                    Task.where("{0} Click and choose particular account to be mapped",
                            waitAndClickUIAction(gateway, gatewayGLAccountMap.get(gateway), flag)
                    );
            performableTaskList.add(manageMapping);
            flag = false;
        }
        return performableTaskList;
    }

    private static Performable waitAndClickUIAction(String gtway, String account, boolean flag) {
        String gateway = gtway.split("\\.")[0];
        String currency = gtway.split("\\.")[1];

        if (flag) {
            return Task.where(
                    WaitUntil.the(select_account_list(gateway, currency), isClickable()).forNoMoreThan(40).seconds(),
                    Click.on(select_account_list(gateway, currency)),
                    Click.on(select_account_list(gateway, currency)),
                    WaitUntil.the(select_account(gateway, account), isClickable()).forNoMoreThan(40).seconds(),
                    Click.on(select_account(gateway, account)));
        } else {
            return Task.where(
                    WaitUntil.the(select_account_list(gateway, currency), isClickable()).forNoMoreThan(40).seconds(),
                    Click.on(select_account_list(gateway, currency)),
                    WaitUntil.the(select_account(gateway, account), isClickable()).forNoMoreThan(40).seconds(),
                    Click.on(select_account(gateway, account)));
        }
    }

    public static Performable save() {

        return Task.where("{0} attempts to save mapped account details", (actor) -> {
            actor.attemptsTo(
                    WaitUntil.the(ManageMappingPage.SAVE_BUTTON, isClickable()).forNoMoreThan(40).seconds(),
                    Click.on(ManageMappingPage.SAVE_BUTTON),
                    WaitUntil.the(QBSyncPage.SYNC_NOW, isClickable()).forNoMoreThan(40).seconds()
            );
        });
    }


    public Performable mapGLAccountForInvoiceLineItems() {
        return null;
    }
}
