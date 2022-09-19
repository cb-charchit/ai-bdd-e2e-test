package com.cbee.clients;

import com.cbee.models.Site;
import com.cbee.utils.ConfigFileReader;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
//import net.serenitybdd.rest.SerenityRest;
import static io.restassured.RestAssured.given;

import java.util.Map;


public class CbClient {

    String baseUrl = "https://neha-singla-pc2-test.chargebee.com/api/v2";
    //String baseUrl = "http://mannar-test.localcb.in:8080/api/v2";
    String apiKey = new ConfigFileReader().getConfigValueByKey("prod.siteApiKey");

    public ExtractableResponse<Response> doHttpGet(String basePath) {
        return given().contentType(ContentType.JSON)
                .auth().digest(apiKey, apiKey)
                .log()
                .all()
                .basePath(basePath)
                .baseUri(baseUrl)
                .when().get().then().extract();
    }

    public ExtractableResponse<Response> doHttpGet(String basePath, Map params) {
        return given().contentType(ContentType.JSON)
                .auth().digest(apiKey, apiKey)
                .log()
                .all()
                .basePath(basePath)
                .baseUri(baseUrl)
                .queryParams(params)
                .when().get().then().extract();
    }

    public ExtractableResponse<Response> doHttpPost(String basePath, Map params) {
        return given()
                .auth().digest(apiKey, apiKey)
                .log()
                .all()
                .basePath(basePath)
                .baseUri(baseUrl)
                .queryParams(params)
                .when().post().then().extract();
    }

}
