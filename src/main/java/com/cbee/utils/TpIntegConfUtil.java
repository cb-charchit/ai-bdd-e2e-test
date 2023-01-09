package com.cbee.utils;

import com.google.gson.Gson;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.LinkedHashMap;

public class TpIntegConfUtil {

    public static JSONObject getConfigJSON(ExtractableResponse<Response> res) {
        return new JSONObject(new Gson().toJson(res.jsonPath().getJsonObject("third_party_configuration.config_json"), LinkedHashMap.class));
    }

    public static JSONObject getIntegMappings(ExtractableResponse<Response> res) {
        return  new JSONObject(new Gson().toJson(res.jsonPath().getJsonObject("third_party_configuration.config_json.IntegMapping.mapping.map"), LinkedHashMap.class));
    }

    public static JSONObject getSyncOverview(ExtractableResponse<Response> res) {
        return  new JSONObject(new Gson().toJson(res.jsonPath().getJsonObject("third_party_configuration.config_json.SyncOverview"), LinkedHashMap.class));
    }


}
