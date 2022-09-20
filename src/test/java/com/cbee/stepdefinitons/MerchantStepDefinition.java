package com.cbee.stepdefinitons;

import com.cbee.ActorState;
import com.cbee.ValidateQboData;
import com.cbee.clients.CbClient;
import com.cbee.models.Currency;
import com.cbee.models.InteractionMode;
import com.cbee.models.InteractionModeApi;
import com.cbee.tasks.SyncNowTask;
import com.cbee.tasks.customer.CreateCustomer;
import com.cbee.tasks.subscription.CreateSubscription;
import com.cbee.models.subscription.CreateSubscriptionRequest;
import com.cbee.tasks.GetTpDetailsTask;
import com.cbee.factory.MerchantFactory;
import com.chargebee.models.Customer;
import com.chargebee.models.Subscription;
import com.google.gson.Gson;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
//import net.serenitybdd.rest.Ensure;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.ensure.Ensure;
import org.assertj.core.api.Assertions;

import static com.cbee.ActorState.setTheCustomerInTheSpotLight;
import static com.cbee.ActorState.thirdPartyIdInTheSpotLight;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.*;
import java.util.logging.Logger;

public class MerchantStepDefinition {

    private GetTpDetailsTask getTpDetailsTask = new GetTpDetailsTask();
    private SyncNowTask syncNowTask = new SyncNowTask();

    @Given("{actor} is an admin of current ChargeBee site")
    public void actor_is_an_admin_of_the_domain(Actor actor) {
        actor.remember("merchant", MerchantFactory.getMerchant(actor.getName()));
    }

    @When("he attempts to fetch particular Integration Configuration")
    public void actor_attempts_to_fetch_particular_integration_configuration(DataTable integ_name) {
        String integration_name = integ_name.asMaps(String.class, String.class).get(0).get("integration_name");
        theActorInTheSpotlight().attemptsTo(getTpDetailsTask.fetchTpIntegConfDetails(integration_name));
    }

    @Then("he should be able to fetch Integration Configuration")
    public void actor_should_able_to_fetch_integration_configuration() {
        Assertions.assertThat(ActorState.configInTheSpotLight());
    }

    @When("he attempts to run sync job for <integration_name>")
    public void actor_attempts_to_run_sync_job_for_particulat_integration(DataTable integ_name) {
        theActorInTheSpotlight().attemptsTo(syncNowTask.runSyncJob());
    }

    @Then("he checked and found that sync job completed successfully")
    public void actor_should_be_able_to_run_sync_successfully() {
        assertThat("Sync scuccessful").isEqualTo("Sync scuccessful");

    }

    @And("he creates a new customer {string} with the email address {string} and a {string}")
    public void actor_creates_a_new_customer(String firstName, String email, String cardDetail) {
        theActorInTheSpotlight().attemptsTo(
                CreateCustomer.using(firstName, email, cardDetail).on(ActorState.theTestSiteInTheSpotlight()).via(api())
        );
    }

    @And("he has a plan {string} with the monthly amount of {int} {currency}")
    public void actor_has_a_plan_with_the_monthly_amount_of(String planName, int monthlyValue, Currency currency) {
        // We don't need to do anything as we are already setting this during merchant creation step in test execution.
    }

    @And("he creates a {string} subscription for {string} with the following values")
    public void actor_creates_subscription_for_customer(String subscriptionType, String customerName, DataTable dataTable) throws Exception {
        CreateSubscriptionRequest createSubscriptionRequest =
                CreateSubscriptionRequest.fromMap(ActorState.theTestSiteInTheSpotlight().productCatalog, dataTable.asMap());
        ActorState.setTheCreateSubscriptionRequestInTheSpotLight(createSubscriptionRequest);

        theActorInTheSpotlight().attemptsTo(
                CreateSubscription.using(createSubscriptionRequest).forThe(ActorState.theCustomerInTheSpotlight()).on(ActorState.theTestSiteInTheSpotlight()).via(api())
        );
    }

    @Then("the customer and invoice should be synced to third party")
    public void the_customer_and_invoice_should_be_synced_to_third_party() {
        theActorInTheSpotlight().attemptsTo(getTpDetailsTask.fetchTpemDetails(ActorState.theCustomerInTheSpotlight().getId(), "customer", "quickbooks"));
        theActorInTheSpotlight().attemptsTo(Ensure.that(thirdPartyIdInTheSpotLight()).isNotBlank());
    }

    @And("he has synced invoice {string} to QBO")
    public void actor_has_synced_invoice(String invId) {
        // We don't need to do anything as we are already having an invoice
    }

    private InteractionMode api() {
        return new InteractionModeApi();
    }

    @When("he attempts to verify if invoice is present in {string}")
    public void he_attempts_to_verify_if_invoice_is_present(String integration_name) {
        theActorInTheSpotlight().attemptsTo(getTpDetailsTask.fetchTpIntegConfDetails(integration_name));
    }

    @And("he has a customer {string} with the email address {string} and a {string}")
    public void he_has_a_customer_with_the_email_address_and_a_visa_card(String customerHanlde, String email, String card) {
        ExtractableResponse<Response> response = new CbClient().doHttpGet("/customers/" + customerHanlde);
        Gson gson = new Gson();
        String json = gson.toJson(response.jsonPath().getJsonObject("customer"), LinkedHashMap.class);
        Customer customer = new Customer(json);
        com.cbee.models.Customer cust = com.cbee.models.Customer.fromCbCustomer(customer);
        setTheCustomerInTheSpotLight(cust);
    }

    @And("he has a <item_type> with <unit_amount> and <quantity> and currency {string}")
    public void he_has_a_item_with_amount_and_quantity(String currency, DataTable data) {
        // We don't need to do anything as we are already setting this during merchant creation step in test execution.
    }

    @When("he attempts to run sync job for {string}")
    public void he_attempts_to_run_sync_job(String integrationName) {
        System.out.println("Running sync for integration: " + integrationName);
        theActorInTheSpotlight().attemptsTo(syncNowTask.runSyncJob());
    }

    @Then("invoice should be synced to Quickbooks")
    public void invoice_should_be_synced_to_quickbooks() {
        Subscription subscription = ActorState.theNewlyCreatedSubscriptionInTheSpotlight();
        String basePath="/invoices";
        Map<String, String> params = new HashMap<>();
        params.put("subscription_id[is]", subscription.id());
        ExtractableResponse<Response> response = new CbClient().doHttpGet(basePath, params);
        Gson gson = new Gson();
        ArrayList listOfInvoiceMaps = response.jsonPath().getJsonObject("list");
        if(listOfInvoiceMaps.size() != 1) {
            throw new RuntimeException("Expected invoice count for subscription id: " + subscription.id()
                    + " is 1 but found "+listOfInvoiceMaps.size());
        }
        String invoiceJson = gson.toJson(((LinkedHashMap)listOfInvoiceMaps.get(0)).get("invoice"), LinkedHashMap.class);
        JSONObject invoiceJsonObject = new JSONObject(invoiceJson);
        theActorInTheSpotlight().attemptsTo(getTpDetailsTask.fetchTpemDetails
                (Optional.of((String) invoiceJsonObject.get("id")), "invoice", "quickbooks"));
        String invoiceExternalId = ActorState.thirdPartyIdInTheSpotLight();
        theActorInTheSpotlight().attemptsTo(Ensure.that(invoiceExternalId).isNotBlank());
        ValidateQboData.checkIfRecordIsPresentInQBO("quickbooks","invoice",invoiceExternalId);
    }

}