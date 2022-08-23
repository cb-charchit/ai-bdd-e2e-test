package com.chargebee.common.features;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;

public class GetTpDetails {

    public static String companyName;

    public static Performable fetchThirdPartyDetails(String integ_name)
    {
        return Task.where("{0} fetch tp details", actor -> {
                    String companyName = getTpDetails(integ_name);
                    setCompanyName(companyName);
                    System.out.println("Company Name is "+companyName);
        });
    }

    public static String getTpDetails(String integ_name) {
        String username = "test_VaPtEoC3nsnYJNRaFP0puyWWwMqPUPTs";
        String password = "test_VaPtEoC3nsnYJNRaFP0puyWWwMqPUPTs";

        ExtractableResponse<Response> response =
                RestAssured.given().contentType(ContentType.JSON)
                        .auth().basic(username, password)
                        .log()
                        .all()
                        .basePath("/third_party_configurations")
                        .baseUri("https://neha-singla-test.chargebee.com/api/v2")
                        .queryParam("integration_name", integ_name)
                        .when().get().then().extract();

        return response.jsonPath().getString("third_party_configuration.config_json.qb_company_name");
    }

    public static void setCompanyName(String cName) {
        companyName = cName;
    }

    public static  String getCompanyName() {
        return companyName;
    }
}
