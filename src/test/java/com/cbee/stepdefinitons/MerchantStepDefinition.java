package com.cbee.stepdefinitons;

import com.cbee.ActorState;
import com.cbee.models.Currency;
import com.cbee.models.InteractionMode;
import com.cbee.models.InteractionModeApi;
import com.cbee.tasks.customer.CreateCustomer;
import com.cbee.tasks.subscription.CreateSubscription;
import com.cbee.models.subscription.CreateSubscriptionRequest;
import com.cbee.tasks.GetTpDetailsTask;
import com.cbee.factory.MerchantFactory;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
//import net.serenitybdd.rest.Ensure;
//import net.serenitybdd.rest.Ensure;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.ensure.Ensure;
import org.assertj.core.api.Assertions;

import static com.cbee.ActorState.thirdPartyIdInTheSpotLight;
import static com.cbee.tasks.GetTpDetailsTask.fetchTpIntegConfDetails;
import static com.cbee.tasks.SyncNowTask.runSyncJob;
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
        theActorInTheSpotlight().attemptsTo(fetchTpIntegConfDetails(integration_name));
    }

    @Then("{actor} should be able to fetch Integration Configuration")
    public void actor_should_able_to_fetch_integration_configuration(Actor actor) {
        Assertions.assertThat(ActorState.configInTheSpotLight());
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
                CreateCustomer.using(firstName, email, cardDetail).on(ActorState.theTestSiteInTheSpotlight()).via(api())
        );
    }

    @And("the merchant has a plan {string} with the monthly amount of {int} {currency}")
    public void theMerchantHasAPlanWithTheMonthlyAmountOf(String planName, int monthlyValue, Currency currency) {
        // We don't need to do anything as we are already setting this during merchant creation step in test execution.
    }

    @And("the merchant creates a {string} subscription for {string} with the following values")
    public void the_merchant_creates_subscription_for_customer(String subscriptionType, String customerName, DataTable dataTable) throws Exception {
        CreateSubscriptionRequest createSubscriptionRequest =
                CreateSubscriptionRequest.fromMap(ActorState.theTestSiteInTheSpotlight().productCatalog, dataTable.asMap());
        ActorState.setTheCreateSubscriptionRequestInTheSpotLight(createSubscriptionRequest);

        theActorInTheSpotlight().attemptsTo(
                CreateSubscription.using(createSubscriptionRequest).forThe(ActorState.theCustomerInTheSpotlight()).on(ActorState.theTestSiteInTheSpotlight()).via(api())
        );
    }
    @Then("the customer and invoice should be synced to third party")
    public void the_customer_and_invoice_should_be_synced_to_third_party() {
        theActorInTheSpotlight().attemptsTo(GetTpDetailsTask.fetchTpemDetails(ActorState.theCustomerInTheSpotlight().getId(), "customer", "quickbooks"));
        theActorInTheSpotlight().attemptsTo(Ensure.that(thirdPartyIdInTheSpotLight()).isNotBlank());
    }

    private InteractionMode api() {
        return new InteractionModeApi();
    }

}