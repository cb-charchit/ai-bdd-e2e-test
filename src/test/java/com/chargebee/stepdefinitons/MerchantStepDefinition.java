package com.chargebee.stepdefinitons;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.rest.Ensure;

import static org.assertj.core.api.Assertions.assertThat;

public class MerchantStepDefinition {

    private Response response;

    @Given("^(?:.*) is the merchant")
    public void actor_is_the_merchant() {
        //actor.remember("merchant", MerchantFactory.getMerchant(actor.getName()));
    }

    @When("^(?:.*) attempts to fetch <integration_name> Integration Configuration")
    public void actor_attempts_to_fetch_particular_integration_configuration(DataTable integ_name) {
        String username = "test_VaPtEoC3nsnYJNRaFP0puyWWwMqPUPTs";
        String password = "test_VaPtEoC3nsnYJNRaFP0puyWWwMqPUPTs";

        response = RestAssured.given().contentType(ContentType.JSON)
                .auth().basic(username, password)
                .log()
                .all()
                .basePath("/third_party_configurations")
                .baseUri("https://neha-singla-test.chargebee.com/api/v2")
                .queryParam("integration_name", integ_name.toString())
                .when().get().then().extract().response();
    }


    @Then("^(?:.*) should able to fetch Integration Configuration")
    public void actor_should_able_to_fetch_integration_configuration() {
        String qb_company_name = response.jsonPath().getString("third_party_configuration.config_json.qb_company_name");
        Ensure.that("Company name is Chargebee Inc", company_name -> qb_company_name.equals(company_name));
        assertThat(qb_company_name).isEqualTo("Chargebee Inc");
        //assertThat("Chargebee Inc").isEqualTo("Chargebee Inc");
    }
}
