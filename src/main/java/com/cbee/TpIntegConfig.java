package com.cbee;

import com.cbee.clients.CbClient;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class TpIntegConfig {

    private final String integration_name = "integration_name";
    private final String auth_json = "auth_json";
    private String basePath="/third_party_configurations";

    //Our API Call is like https://neha-singla-pc2-test.chargebee.com/api/v2/third_party_configurations?integration_name=quickbooks
    //In auth - Basic Auth with username and password
    public ExtractableResponse<Response> getTpIntegConfigs(String integ_name) {
        Map<String, String> params = new HashMap<>();
        params.put(integration_name, integ_name);
        return new CbClient().doHttpGet(basePath, params);
    }

    public ExtractableResponse<Response> updateTpIntegConfigs(String integ_name, String authJson) {
        Map<String, String> params = new HashMap<>();
        params.put(integration_name, integ_name);
        params.put(auth_json, authJson);
        return new CbClient().doHttpPost(basePath, params);
    }
}
