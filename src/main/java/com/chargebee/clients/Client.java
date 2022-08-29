package com.chargebee.clients;

import com.chargebee.Site;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
//import net.serenitybdd.rest.SerenityRest;
import static io.restassured.RestAssured.given;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class Client {

    String baseUrl = "https://neha-singla-pc2-test.chargebee.com/api/v2";

    public ExtractableResponse<Response> doHttpGet(String basePath, Map params) {
        return given().contentType(ContentType.JSON)
                .auth().digest(Site.getEnvironment().apiKey, Site.getEnvironment().apiKey)
                .log()
                .all()
                .basePath(basePath)
                .baseUri(baseUrl)
                .queryParams(params)
                .when().get().then().extract();
    }

}
