package com.cbee.tasks.subscription;

import com.cbee.pages.ManageMappingPage;
import com.cbee.pages.QBSyncPage;
import com.cbee.tasks.LoginTask;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.cbee.pages.ManageMappingPage.*;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;

public class ConfigureTrackingCategory {

        public Performable mapTrackingCategoryForInvoiceLineItems(Map<String, String> lineItemTrackingCategoryMap, Map<String, String> lineItemTrackingCategoryIdMap) {

            return Task.where("{0} map tracking category for Invoice Line Items", actor -> {
                actor.attemptsTo(Open.browserOn().the(ManageMappingPage.class),
                        LoginTask.login(),
                        saveLineItemsTrackingCategory(lineItemTrackingCategoryMap, lineItemTrackingCategoryIdMap),
                        save());
            });
        }

    private Performable saveLineItemsTrackingCategory(Map<String, String> lineItemTrackingCategoryMap,Map<String, String> lineItemTrackingCategoryIdMap ) {
        List<Performable> performableTaskList = dynamicTrackingCategoryMappingForLineItems(lineItemTrackingCategoryMap, lineItemTrackingCategoryIdMap);

        return Task.where("{0} attempts to select account", (actor) -> {
            for (Performable performableTask : performableTaskList) {
                actor.attemptsTo(performableTask);
            }
        });
    }

    public static List<Performable> dynamicTrackingCategoryMappingForLineItems(Map<String, String> lineItemTrackingCategoryMap, Map<String, String> lineItemTrackingCategoryIdMap) {
        List<Performable> performableTaskList = new LinkedList<>();
        boolean flag = true;

        for (String lineItem : lineItemTrackingCategoryMap.keySet()) {
            Performable manageMapping =
                    Task.where("{0} Click and choose particular account to be mapped",
                            waitAndClickUIActionForLineItems(lineItemTrackingCategoryIdMap.get(lineItem), lineItemTrackingCategoryMap.get(lineItem), flag)
                    );
            performableTaskList.add(manageMapping);
            flag = false;
        }
        return performableTaskList;
    }

    private static Performable waitAndClickUIActionForLineItems(String lineItemId, String category, boolean flag) {

        if (flag) {
            return Task.where(
                    WaitUntil.the(selectTrackingCategoryClass(lineItemId), isClickable()).forNoMoreThan(40).seconds(),
                    Click.on(selectTrackingCategoryClass(lineItemId)),
                    Click.on(selectTrackingCategoryClass(lineItemId)),
                    Enter.theValue(category).into(selectTrackingCategoryClassField(lineItemId)),
                    WaitUntil.the(selectTrackingCategoryClassFieldApplyButton(lineItemId), isClickable()).forNoMoreThan(40).seconds(),
                    Click.on(selectTrackingCategoryClassFieldApplyButton(lineItemId)));
        } else {
            return Task.where(
                    WaitUntil.the(selectTrackingCategoryClass(lineItemId), isClickable()).forNoMoreThan(40).seconds(),
                    Click.on(selectTrackingCategoryClass(lineItemId)),
                    Enter.theValue(category).into(selectTrackingCategoryClassField(lineItemId)),
                    WaitUntil.the(selectTrackingCategoryClassFieldApplyButton(lineItemId), isClickable()).forNoMoreThan(40).seconds(),
                    Click.on(selectTrackingCategoryClassFieldApplyButton(lineItemId)));
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
}
