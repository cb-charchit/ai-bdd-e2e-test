package com.cbee.clients;

import com.cbee.utils.ConfigFileReader;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;


public class CbClient {

    String baseUrl = "https://neha-singla-pc2-test.chargebee.com/api/v2";
    //String baseUrl = "http://mannar-test.localcb.in:8080/api/v2";
    String apiKey = new ConfigFileReader().getConfigValueByKey("siteApiKey");
    String siteName = new ConfigFileReader().getConfigValueByKey("siteName");

    public ExtractableResponse<Response> doHttpGet(String basePath) {
        return doHttpGet(basePath, null);
    }

    public ExtractableResponse<Response> doHttpGet(String basePath, Map<String, String> params) {
        RequestSpecification request =  given().contentType(ContentType.JSON)
                .auth().digest(apiKey, apiKey)
                .log()
                .all()
                .basePath(basePath)
                .baseUri(baseUrl);
        if(params != null) {
            request.queryParams(params);
        }
        ExtractableResponse<Response> response = request.when().get().then().extract();
        if(response.statusCode() != 200) {
            throw new RuntimeException("Chargebee API failed with code: "+ response.statusCode() + " and error: "+response.jsonPath().prettify());
        }
        return response;
    }

    public ExtractableResponse<Response> doHttpPost(String basePath, Map<String, String> params) {
        RequestSpecification request =  given()
                .auth().digest(apiKey, apiKey)
                .log()
                .all()
                .basePath(basePath)
                .baseUri(baseUrl)
                .queryParams(params);
        if(params != null) {
            request.queryParams(params);
        }
        ExtractableResponse<Response> response = request.when().post().then().extract();
        if(response.statusCode() != 200) {
            throw new RuntimeException("Chargebee API failed with code: "+ response.statusCode() + " and error: "+response.jsonPath().prettify());
        }
        return response;
    }

}
