package com.chargebee.stepdefinitons;

import com.chargebee.Currency;
import com.chargebee.InteractionMode;
import com.chargebee.InteractionModeApi;
import com.chargebee.common.UI.LoginPage;
import com.chargebee.common.features.GetTpDetails;
import com.chargebee.customer.CreateCustomer;
import com.chargebee.qbo.UI.QBSyncPage;
import com.chargebee.subscription.CreateSubscription;
import com.chargebee.subscription.CreateSubscriptionRequest;
import com.factory.MerchantFactory;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.ensure.Ensure;

import java.util.Optional;

import static com.chargebee.ActorState.*;
import static com.chargebee.ActorState.theCustomerInTheSpotlight;
import static com.chargebee.common.features.GetTpDetails.fetchThirdPartyDetails;
import static com.chargebee.common.features.Sync.runSyncJob;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.assertj.core.api.Assertions.assertThat;


public class MerchantStepDefinition {

    @Given("{actor} is a merchant")
    public void actor_is_a_merchant(Actor actor) {
        actor.remember("merchant", MerchantFactory.getMerchant(actor.getName()));
    }

    @When("{actor} attempts to fetch particular Integration Configuration")
    public void actor_attempts_to_fetch_particular_integration_configuration(Actor actor,DataTable integ_name) {
        String integration_name = integ_name.asMaps(String.class, String.class).get(0).get("integration_name");
        theActorInTheSpotlight().attemptsTo(fetchThirdPartyDetails(integration_name));
    }

    @Then("{actor} should able to fetch Integration Configuration")
    public void actor_should_able_to_fetch_integration_configuration(Actor actor) {
        assertThat(configInTheSpotLight());
    }

    @When("{actor} attempts to run sync job for <integration_name>")
    public void actor_attempts_to_run_sync_job_for_particulat_integration(Actor actor, DataTable integ_name) {
        theActorInTheSpotlight().attemptsTo(runSyncJob());
    }

    @Then("{actor} should be able to run sync successfully")
    public void actor_should_be_able_to_run_sync_successfully(Actor actor) {
        assertThat("Sync scuccessful").isEqualTo("Sync scuccessful");

    }

    @And("the merchant creates a new customer {string} with the email address {string} and a {string}")
    public void the_merchant_creates_a_new_customer(String firstName, String email, String cardDetail) {
        theActorInTheSpotlight().attemptsTo(
                CreateCustomer.using(firstName, email, cardDetail).on(theTestSiteInTheSpotlight()).via(api())
        );
    }

    @And("the merchant has a plan {string} with the monthly amount of {int} {currency}")
    public void theMerchantHasAPlanWithTheMonthlyAmountOf(String planName, int monthlyValue, Currency currency) {
        // We don't need to do anything as we are already setting this during merchant creation step in test execution.
    }

    @And("the merchant creates a {string} subscription for {string} with the following values")
    public void the_merchant_creates_subscription_for_customer(String subscriptionType, String customerName, DataTable dataTable) throws Exception {
        CreateSubscriptionRequest createSubscriptionRequest =
                CreateSubscriptionRequest.fromMap(theTestSiteInTheSpotlight().productCatalog, dataTable.asMap());
        setTheCreateSubscriptionRequestInTheSpotLight(createSubscriptionRequest);

        theActorInTheSpotlight().attemptsTo(
                CreateSubscription.using(createSubscriptionRequest).forThe(theCustomerInTheSpotlight()).on(theTestSiteInTheSpotlight()).via(api())
        );
    }
    @Then("the customer and invoice should be synced to third party")
    public void the_customer_and_invoice_should_be_synced_to_third_party() {
        theActorInTheSpotlight().attemptsTo(GetTpDetails.fetchTpemDetails(theCustomerInTheSpotlight().getId(), "customer", "quickbooks"));
        assertThat(!thirdPartyIdInTheSpotLight().equalsIgnoreCase(""));
    }

    private InteractionMode api() {
        return new InteractionModeApi();
    }

}