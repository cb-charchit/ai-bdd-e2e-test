package com.cbee.toBeDeletedLater;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.ensure.Ensure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static net.serenitybdd.rest.SerenityRest.given;

@RunWith(SerenityRunner.class)
public class VerifyCompanyDetails {

    public static String companyName;

    @Before
    public void prepareRestConfig() {
        RestAssured.baseURI = "https://neha-singla-test.chargebee.com/api/v2";
    }

    @Test
    public void verify_company_details() {

        Actor customer = Actor.named("Customer");

        customer.attemptsTo(getCompanyNameViApi());
        customer.attemptsTo(Ensure.that(companyName).isEqualTo("Chargebee Inc"));
        customer.attemptsTo(Ensure.that("Neha").isEqualTo("Neha"));
    }

    public static Performable getCompanyNameViApi() {
        String username = "test_VaPtEoC3nsnYJNRaFP0puyWWwMqPUPTs";
        String password = "test_VaPtEoC3nsnYJNRaFP0puyWWwMqPUPTs";

        return Task.where("{0} get company Name via API ", actor -> {
            ExtractableResponse<Response> response =
                    given().contentType(ContentType.JSON)
                            .auth().basic(username,password)
                            .log()
                            .all()
                            .basePath("/third_party_configurations")
                            .queryParam("integration_name", "quickbooks")
                            .when().get().then().extract();
            companyName = response.jsonPath().getString("third_party_configuration.config_json.qb_company_name");

        });
    }
}
