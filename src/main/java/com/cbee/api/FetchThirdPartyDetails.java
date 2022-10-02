package com.cbee.api;

import com.cbee.clients.CbClient;
import com.google.gson.Gson;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class FetchThirdPartyDetails {

    private static final String integration_name = "integration_name";
    private static final String auth_json = "auth_json";

    //Our API Call is like https://neha-singla-pc2-test.chargebee.com/api/v2/third_party_configurations?integration_name=quickbooks
    //In auth - Basic Auth with username and password
    public static ExtractableResponse<Response> getTpIntegConfigs(String integ_name) {
        String basePath="/third_party_configurations";
        Map<String, String> params = new HashMap<>();
        params.put(integration_name, integ_name);
        return new CbClient().doHttpGet(basePath, params);
    }

    public static ExtractableResponse<Response> updateTpIntegConfigs(String integ_name, String authJson) {
        String basePath="/third_party_configurations";
        Map<String, String> params = new HashMap<>();
        params.put(integration_name, integ_name);
        params.put(auth_json, authJson);
        return new CbClient().doHttpPost(basePath, params);
    }


}
