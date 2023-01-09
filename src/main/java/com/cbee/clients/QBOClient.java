package com.cbee.clients;

import com.cbee.Token;
import com.cbee.utils.ConfigFileReader;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

import static io.restassured.RestAssured.given;


public class QBOClient {

    private final String Authorization = "Authorization";
    private final String Bearer = "Bearer";

    public ExtractableResponse<Response> doHttpGet(String basePath, Map<String, String> params, String accessToken) {
        params.put("minorversion", "65");
        ExtractableResponse<Response> res = given().contentType(ContentType.JSON)
                .accept("application/json")
                .header(Authorization, Bearer + " " + accessToken)
                .log()
                .all()
                .basePath(basePath)
                .baseUri(new ConfigFileReader().getConfigValueByKey("baseUrl"))
                .queryParams(params)
                .when().get().then().extract();
        return res;
    }

    public ExtractableResponse<Response> doHttpPost(Token token) throws IOException {

        ConfigFileReader conf = new ConfigFileReader();
        return given()
                .contentType(ContentType.URLENC)
                .accept(ContentType.JSON)
                .header(Authorization, "Basic " + getEncodedBase64Value(conf.getClientKey()+":"+conf.getClientSecret()))
                .param("grant_type", "refresh_token")
                .param("refresh_token", token.getRefreshToken())
                .log()
                .all()
                .baseUri("https://oauth.platform.intuit.com")
                .basePath("/oauth2/v1/tokens/bearer")
                .when().post().then().extract();
    }

    //move to utils
    public String getEncodedBase64Value(String key) throws IOException {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(key.getBytes());
    }

}
