package com.chargebee.common.features;

import com.chargebee.clients.Client;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static com.chargebee.ActorState.setConfigInTheSpotLight;
import static com.chargebee.ActorState.setThirdPartyIdInTheSpotLight;

public class GetTpDetails {

    static Map<String, String> params = new HashMap<>();

    public static Performable fetchThirdPartyDetails(String integ_name) {
        return Task.where("{0} fetch tp details", actor -> {
            boolean isConfigAvailable = getTpDetails(integ_name);
            setConfigInTheSpotLight(isConfigAvailable);
        });
    }

    public static boolean getTpDetails(String integ_name) {
        String basePath = "/third_party_configurations";
        params.put("integration_name", integ_name);
        ExtractableResponse<Response> response = new Client().doHttpGet(basePath, params);
        return (response.jsonPath().getString("third_party_configuration.config_json") != null);
    }

    public static Performable fetchTpemDetails(Optional<String> id, String entity_type, String integ_name) {
        return Task.where("{0} fetch tpem details", actor -> {
            String third_party_entity_id = getTpemData(id, entity_type, integ_name);
            setThirdPartyIdInTheSpotLight(third_party_entity_id);
        });
    }

    public static String getTpemData(Optional<String> id, String entity_type, String integ_name) {
        String basePath = "/third_party_entity_mappings/retrieve";
        params.put("integration_name", integ_name);
        params.put("entity_type", entity_type);
        params.put("entity_id", id.get());
        ExtractableResponse<Response> response = new Client().doHttpGet(basePath, params);
        return response.jsonPath().getString("third_party_entity_mapping.third_party_entity_id");
    }
}
