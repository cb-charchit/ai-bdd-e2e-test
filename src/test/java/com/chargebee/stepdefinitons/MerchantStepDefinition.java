package com.chargebee.stepdefinitons;

import com.chargebee.common.UI.LoginPage;
import com.chargebee.common.features.GetTpDetails;
import com.chargebee.qbo.UI.QBSyncPage;
import com.factory.MerchantFactory;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actions.Open;

import static com.chargebee.common.features.GetTpDetails.fetchThirdPartyDetails;
import static com.chargebee.common.features.Sync.runSyncJob;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.assertj.core.api.Assertions.assertThat;

public class MerchantStepDefinition {

    @Given("{actor} is a merchant")
    public void actor_is_a_merchant(Actor actor) {
        actor.remember("merchant", MerchantFactory.getMerchant(actor.getName()));
    }

    @When("{actor} attempts to fetch <integration_name> Integration Configuration")
    public void actor_attempts_to_fetch_particular_integration_configuration(Actor actor,DataTable integ_name) {
        String integration_name = integ_name.asMaps(String.class, String.class).get(0).get("integration_name");
        theActorInTheSpotlight().attemptsTo(fetchThirdPartyDetails(integration_name));
    }

    @Then("{actor} should able to fetch Integration Configuration")
    public void actor_should_able_to_fetch_integration_configuration(Actor actor) {
        assertThat(GetTpDetails.getCompanyName()).isEqualTo("Chargebee Inc");
    }

    @When("{actor} attempts to run sync job for <integration_name>")
    public void actor_attempts_to_run_sync_job_for_particulat_integration(Actor actor, DataTable integ_name) {
        theActorInTheSpotlight().attemptsTo(runSyncJob());
    }

    @Then("{actor} should be able to run sync successfully")
    public void actor_should_be_able_to_run_sync_successfully(Actor actor) {
        assertThat("Sync scuccessful").isEqualTo("Sync scuccessful");

    }
}