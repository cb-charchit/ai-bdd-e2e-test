package com.cbee.tasks;

import com.cbee.ActorState;
import com.cbee.clients.CbClient;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static io.restassured.RestAssured.given;

public class GetTpDetailsTask {

    static Map<String, String> params = new HashMap<>();

    public static Performable fetchTpIntegConfDetails(String integ_name) {
        return Task.where("{0} fetches third party integration configuration", actor -> {
            boolean isConfigAvailable = getTpConfigs(integ_name);
            ActorState.setConfigInTheSpotLight(isConfigAvailable);
        });
    }

    public static Performable fetchTpemDetails(Optional<String> id, String entity_type, String integ_name) {
        return Task.where("{0} fetches synced entities id's from tpem", actor -> {
            String third_party_entity_id = getTpemData(id, entity_type, integ_name);
            ActorState.setThirdPartyIdInTheSpotLight(third_party_entity_id);
        });
    }

    public static boolean getTpConfigs(String integ_name) {
        String basePath = "/third_party_configurations";
        params.put("integration_name", integ_name);
        ExtractableResponse<Response> response = new CbClient().doHttpGet(basePath, params);
        return (response.jsonPath().getString("third_party_configuration.config_json") != null);
    }

    public static String getTpemData(Optional<String> id, String entity_type, String integ_name) {
        String basePath = "/third_party_entity_mappings/retrieve";
        params.put("integration_name", integ_name);
        params.put("entity_type", entity_type);
        params.put("entity_id", id.get());
        ExtractableResponse<Response> response = new CbClient().doHttpGet(basePath, params);
        return response.jsonPath().getString("third_party_entity_mapping.third_party_entity_id");
    }
}