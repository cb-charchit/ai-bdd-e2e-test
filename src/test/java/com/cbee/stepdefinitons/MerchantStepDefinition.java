package com.cbee.stepdefinitons;

import com.cbee.ActorState;
import com.cbee.GatewayAccountMapping;
import com.cbee.api.FetchThirdPartyDetails;
import com.cbee.api.FetchEntitiesFromQuickbooks;
import com.cbee.clients.CbClient;
import com.cbee.integ.models.quickbooks.QBInvoice;
import com.cbee.integ.models.quickbooks.QBLine;
import com.cbee.models.InteractionMode;
import com.cbee.models.InteractionModeApi;
import com.cbee.tasks.MapGLAccountTask;
import com.cbee.tasks.SyncNowTask;
import com.cbee.tasks.subscription.CreateSubscription;
import com.cbee.models.subscription.CreateSubscriptionRequest;
import com.cbee.tasks.GetTpDetailsTask;
import com.cbee.factory.MerchantFactory;
import com.cbee.utils.TpIntegConfUtil;
import com.chargebee.models.Customer;
import com.chargebee.models.Invoice;
import com.chargebee.models.Subscription;
import com.google.gson.Gson;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
//import net.serenitybdd.rest.Ensure;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.ensure.Ensure;
import org.assertj.core.api.Assertions;

import static com.cbee.ActorState.setTheCustomerInTheSpotLight;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.*;

public class MerchantStepDefinition {

    private SyncNowTask syncNowTask = new SyncNowTask();
    Map<String, String> gatewayGLAccountMap = new HashMap<>();

    @Given("{actor} is an admin of ChargeBee site")
    public void actor_is_an_admin_of_the_domain(Actor actor) {
        actor.remember("merchant", MerchantFactory.getMerchant(actor.getName()));
    }

    @And("he has a customer {string} with the email address {string}")
    public void he_has_a_customer_with_the_email_address(String customerHandle, String email) {
        fetch_customer_from_CB_and_set_in_spotlight(customerHandle);
    }

    @And("he has a <item> with <amount> and currency {string}")
    public void he_has_a_item_type_with_amount_and_currency(String currency, DataTable data) {
    }

    @And("he creates a new subscription for {string} with the following values")
    public void actor_creates_subscription_for_customer(String customerName, DataTable dataTable) throws Exception {
        CreateSubscriptionRequest createSubscriptionRequest =
                CreateSubscriptionRequest.fromMap(ActorState.theTestSiteInTheSpotlight().productCatalog, dataTable.asMap());
        ActorState.setTheCreateSubscriptionRequestInTheSpotLight(createSubscriptionRequest);

        theActorInTheSpotlight().attemptsTo(
                CreateSubscription.using(createSubscriptionRequest).forThe(ActorState.theCustomerInTheSpotlight()).on(ActorState.theTestSiteInTheSpotlight()).via(api())
        );
    }

    @When("he attempts to run sync job for Quickbooks")
    public void he_attempts_to_run_sync_job() {
        theActorInTheSpotlight().attemptsTo(syncNowTask.runSyncJob());
    }

    @Then("invoice with amount {long} should be synced to Quickbooks")
    public void invoice_with_amount_should_be_synced_to_quickbooks(long amount) {
        Subscription subscription = ActorState.theNewlyCreatedSubscriptionInTheSpotlight();
        String basePath = "/invoices";
        Map<String, String> params = new HashMap<>();
        params.put("subscription_id[is]", subscription.id());
        ExtractableResponse<Response> response = new CbClient().doHttpGet(basePath, params);
        Gson gson = new Gson();
        ArrayList listOfInvoiceMaps = response.jsonPath().getJsonObject("list");
        if (listOfInvoiceMaps.size() != 1) {
            throw new RuntimeException("Expected invoice count for subscription id: " + subscription.id()
                    + " is 1 but found " + listOfInvoiceMaps.size());
        }
        String invoiceJson = gson.toJson(((LinkedHashMap) listOfInvoiceMaps.get(0)).get("invoice"), LinkedHashMap.class);
        Invoice cbInvoice = new Invoice(invoiceJson);
        String qbInvId = new GetTpDetailsTask().getThirdPartyEntityIdFromTPEM(Optional.of(cbInvoice.id()), "invoice", "quickbooks");
        theActorInTheSpotlight().attemptsTo(Ensure.that(qbInvId).isNotBlank());
        QBInvoice qbInvoice = new FetchEntitiesFromQuickbooks().fetchQuickbooksInvoiceByQBInvId(qbInvId);
        assertThat(qbInvoice).isNotNull();
        long syncedInvoiceAmount = qbInvoice.totalAmount();
        qbInvoice.qbLines().get(0).qbLineDetail().lineItemRef().opt("name");
        theActorInTheSpotlight().attemptsTo(Ensure.that(syncedInvoiceAmount).isEqualTo(amount));
        theActorInTheSpotlight().attemptsTo(Ensure.that(qbInvoice.subTotalLineDetail().getInt("Amount")*100).isEqualTo(cbInvoice.subTotal()),
                Ensure.that(qbInvoice.currencyRef().getString("value")).isEqualTo(cbInvoice.currencyCode()),
                Ensure.that(qbInvoice.customerRef().getString("name")).isEqualTo(cbInvoice.customerId()),//<todo> customer verification via tpem
                Ensure.that(qbInvoice.exchangeRate()).isEqualTo(cbInvoice.exchangeRate())
        );


        qbInvoice.docNumber(); // conf
        //qbInvoice.billingAddress().equals(cbInvoice.billingAddress());
        //qbInvoice.shipFromAddress();
        for(int i=0;i<cbInvoice.lineItems().size();i++) {
            QBLine qbLineItem = qbInvoice.qbLines().get(i);
            Invoice.LineItem cbLineItem = cbInvoice.lineItems().get(i);
            QBLine.QBLineDetail qbLineDetail = qbLineItem.qbLineDetail();
            int itemRefTPId = qbLineDetail.lineItemRef().getInt("value");
            String itemRefName = qbLineDetail.lineItemRef().getString("name");
            int itemAccRefTPId = qbLineDetail.lineItemAccountRef().getInt("value");
            String itemAccRefName = qbLineDetail.lineItemAccountRef().getString("name");

            theActorInTheSpotlight().attemptsTo(Ensure.that(qbLineItem.amount()).isEqualTo(Long.valueOf(cbLineItem.amount())),
                    Ensure.that(itemRefName).isEqualTo(cbLineItem.entityId())//<todo> item verification via tpem
            );

        }
    }


    @And("he has a <item> with <unit_amount> and <quantity> and currency {string}")
    public void he_has_a_item_with_amount_and_quantity(String currency, DataTable data) {
        // We don't need to do anything as we are already setting this during merchant creation step in test execution.
    }

    @And("he has a metered pricing_model with the following tiers")
    public void he_has_a_pricing_model_with_tiers(DataTable data) {
        // We don't need to do anything as we are already setting this during merchant creation step in test execution.
    }


    /*@When("he attempts to fetch particular Integration Configuration")
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



    /*@Then("the customer and invoice should be synced to third party")
    public void the_customer_and_invoice_should_be_synced_to_third_party() {
        theActorInTheSpotlight().attemptsTo(getTpDetailsTask.fetchTpemDetails(ActorState.theCustomerInTheSpotlight().getId(), "customer", "quickbooks"));
        theActorInTheSpotlight().attemptsTo(Ensure.that(thirdPartyIdInTheSpotLight()).isNotBlank());
    }

    @And("he has synced invoice {string} to QBO")
    public void actor_has_synced_invoice(String invId) {
        // We don't need to do anything as we are already having an invoice
    }


    @When("he attempts to verify if invoice is present in {string}")
    public void he_attempts_to_verify_if_invoice_is_present(String integration_name) {
        theActorInTheSpotlight().attemptsTo(getTpDetailsTask.fetchTpIntegConfDetails(integration_name));
    }

    @And("he has a plan with {string} and quantity {int}")
    public void heHasAPlanWithPricing_modelAndQuantityOf(String model, int arg0, DataTable data) {
    }*/



    @When("he attempts to map GL Account for different payment gateways")
    public void he_attempts_to_map_GL_account_for_different_payment_gateways(List<GatewayAccountMapping> gatewayAccountData) {
        theActorInTheSpotlight().attemptsTo(new MapGLAccountTask().mapGLAccountForPayments(gatewayGLAccountMap));
    }

    @DataTableType
    public GatewayAccountMapping gatewayAccountMapping(Map<String, String> gatewayAccountData) {
        String gateway = gatewayAccountData.get("Gateway");
        String currency = gatewayAccountData.get("Currency");
        String account = gatewayAccountData.get("Account");
        gatewayGLAccountMap.put((gateway + "." + currency).toLowerCase(), account);
        return new GatewayAccountMapping(gateway, currency, account);
    }

    @Then("mapping should be saved in DB")
    public void mapping_should_be_saved_in_DB() {
        ExtractableResponse<Response> res = FetchThirdPartyDetails.getTpIntegConfigs("quickbooks");
        JSONObject integMappings = TpIntegConfUtil.getIntegMappings(res);
        for(String gatewayCurrency : gatewayGLAccountMap.keySet()){
            if(gatewayGLAccountMap.get(gatewayCurrency) == integMappings.get(gatewayCurrency))
                System.out.println("mapped correctly");
        }
        //System.out.println(configJson);
    }

    @And("he has a customer {string} belong to {string}")
    public void he_has_a_customer_belong_to_tax_inclusive_region(String customerHandle, String taxRegion) {
        fetch_customer_from_CB_and_set_in_spotlight(customerHandle);
    }

    public void fetch_customer_from_CB_and_set_in_spotlight(String customerHandle) {
        ExtractableResponse<Response> response = new CbClient().doHttpGet("/customers/" + customerHandle);
        Gson gson = new Gson();
        String json = gson.toJson(response.jsonPath().getJsonObject("customer"), LinkedHashMap.class);
        Customer customer = new Customer(json);
        com.cbee.models.Customer cust = com.cbee.models.Customer.fromCbCustomer(customer);
        setTheCustomerInTheSpotLight(cust);
    }

    @When("he attempts to map GL Account for different invoice Line Items")
    public void heAttemptsToMapGLAccountForDifferentInvoiceLineItems() {
        //theActorInTheSpotlight().attemptsTo(new MapGLAccountTask().mapGLAccountForPayments());
    }

    private InteractionMode api() {
        return new InteractionModeApi();
    }

}