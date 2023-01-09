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

import static com.cbee.pages.ManageMappingPage.*;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;

public class MapGLAccountTask {

    public Performable mapGLAccountForPayments(Map<String, String> gatewayGLAccountMap) {
        return Task.where("{0} map GL Account for payments and refunds", actor -> {
            actor.attemptsTo(Open.browserOn().the(ManageMappingPage.class),
                    LoginTask.login(),
                    selectGatewayAccountMapping(gatewayGLAccountMap),
                    save());
        });
    }

    public static Performable selectGatewayAccountMapping(Map<String, String> gatewayGLAccountMap) {
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
                    WaitUntil.the(selectAccountListForPayments(gateway, currency), isClickable()).forNoMoreThan(40).seconds(),
                    Click.on(selectAccountListForPayments(gateway, currency)),
                    Click.on(selectAccountListForPayments(gateway, currency)),
                    WaitUntil.the(selectAccountForPayments(gateway, account), isClickable()).forNoMoreThan(40).seconds(),
                    Click.on(selectAccountForPayments(gateway, account)));
        } else {
            return Task.where(
                    WaitUntil.the(selectAccountListForPayments(gateway, currency), isClickable()).forNoMoreThan(40).seconds(),
                    Click.on(selectAccountListForPayments(gateway, currency)),
                    WaitUntil.the(selectAccountForPayments(gateway, account), isClickable()).forNoMoreThan(40).seconds(),
                    Click.on(selectAccountForPayments(gateway, account)));
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


    public Performable mapGLAccountForInvoiceLineItems(Map<String, String> lineItemGLAccountMap, Map<String, String> lineItemIdMap) {

        return Task.where("{0} map GL Account for Invoice Line Items", actor -> {
            actor.attemptsTo(Open.browserOn().the(ManageMappingPage.class),
                    LoginTask.login(),
                    saveLineItemsAccountMappingDetails(lineItemGLAccountMap, lineItemIdMap),
                    save());
        });
    }

    private Performable saveLineItemsAccountMappingDetails(Map<String, String> lineItemGLAccountMap,Map<String, String> lineItemIdMap ) {
        List<Performable> performableTaskList = dynamicAccountMappingForLineItems(lineItemGLAccountMap,lineItemIdMap);

        return Task.where("{0} attempts to select account", (actor) -> {
            for (Performable performableTask : performableTaskList) {
                actor.attemptsTo(performableTask);
            }
        });
    }

    public static List<Performable> dynamicAccountMappingForLineItems(Map<String, String> lineItemGLAccountMap, Map<String, String> lineItemIdMap) {
        List<Performable> performableTaskList = new LinkedList<>();
        boolean flag = true;

        for (String lineItem : lineItemGLAccountMap.keySet()) {
            Performable manageMapping =
                    Task.where("{0} Click and choose particular account to be mapped",
                            waitAndClickUIActionForLineItems(lineItemIdMap.get(lineItem), lineItemGLAccountMap.get(lineItem), flag)
                    );
            performableTaskList.add(manageMapping);
            flag = false;
        }
        return performableTaskList;
    }

    private static Performable waitAndClickUIActionForLineItems(String lineItemId, String account, boolean flag) {
        if (flag) {
            return Task.where(
                    WaitUntil.the(selectAccountListForLineItems(lineItemId), isClickable()).forNoMoreThan(40).seconds(),
                    Click.on(selectAccountListForLineItems(lineItemId)),
                    Click.on(selectAccountListForLineItems(lineItemId)),
                    WaitUntil.the(selectAccountForLineItems(lineItemId, account), isClickable()).forNoMoreThan(40).seconds(),
                    Click.on(selectAccountForLineItems(lineItemId, account)));
        } else {
            return Task.where(
                    WaitUntil.the(selectAccountListForLineItems(lineItemId), isClickable()).forNoMoreThan(40).seconds(),
                    Click.on(selectAccountListForLineItems(lineItemId)),
                    WaitUntil.the(selectAccountForLineItems(lineItemId, account), isClickable()).forNoMoreThan(40).seconds(),
                    Click.on(selectAccountForLineItems(lineItemId, account)));
        }
    }



}
