package com.chargebee.common.features;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import net.serenitybdd.screenplay.ensure.Ensure;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

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
        Ensure.that(qb_company_name.equals("Chargebee Inc"));
    }
}
