package com.chargebee.common.features;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.Ensure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static net.serenitybdd.rest.SerenityRest.given;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SerenityRunner.class)
public class WhenGettingTpIntegConfigDetails {

    @Before
    public void prepareRestConfig() {
        RestAssured.baseURI = "https://neha-singla-test.chargebee.com/api/v2";
    }

    @Test
    public void should_get_tp_integ_configs() {
        String username = "test_VaPtEoC3nsnYJNRaFP0puyWWwMqPUPTs";
        String password = "test_VaPtEoC3nsnYJNRaFP0puyWWwMqPUPTs";

        ExtractableResponse<Response> response =
                given().contentType(ContentType.JSON)
                        .auth().basic(username, password)
                        .log()
                        .all()
                        .basePath("/third_party_configurations")
                        .queryParam("integration_name", "quickbooks")
                        .when().get().then().extract();

        String qb_company_name = response.jsonPath().getString("third_party_configuration.config_json.qb_company_name");
        Ensure.that("Company name is Chargebee Inc",company_name -> company_name.equals(qb_company_name));
       // Ensure.that("Company name is Chargebee Inc",response1 -> response1.body("third_party_configuration.config_json.qb_company_name",equalTo("Chargebee Inc")));
    }
}
