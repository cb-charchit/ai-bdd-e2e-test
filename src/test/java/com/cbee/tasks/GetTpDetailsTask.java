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

    private Map<String, String> params = new HashMap<>();

    public Performable fetchTpIntegConfDetails(String integ_name) {
        return Task.where("{0} fetches third party integration configuration", actor -> {
            boolean isConfigAvailable = getTpConfigs(integ_name);
            ActorState.setConfigInTheSpotLight(isConfigAvailable);
        });
    }

    public Performable fetchTpemDetails(Optional<String> id, String entity_type, String integ_name) {
        return Task.where("{0} fetches synced entities id's from tpem", actor -> {
            String third_party_entity_id = getThirdPartyEntityIdFromTPEM(id, entity_type, integ_name);
            ActorState.setThirdPartyIdInTheSpotLight(third_party_entity_id);
        });
    }

    public boolean getTpConfigs(String integ_name) {
        String basePath = "/third_party_configurations";
        params.put("integration_name", integ_name);
        ExtractableResponse<Response> response = new CbClient().doHttpGet(basePath, params);
        return (response.jsonPath().getString("third_party_configuration.config_json") != null);
    }

    public Performable getAuthJson(String integ_name) {
        return Task.where("{0} fetches auth json configuration", actor -> {
            String basePath = "/third_party_configurations";
            params.put("integration_name", integ_name);
            ExtractableResponse<Response> response = new CbClient().doHttpGet(basePath, params);
            String authJson=  response.jsonPath().getString("third_party_configuration.auth_json.access_token");
           // new QboIntegration().setAccessToken(authJson);
        });
    }

    public String getThirdPartyEntityIdFromTPEM(Optional<String> id, String entity_type, String integ_name) {
        String basePath = "/third_party_entity_mappings/retrieve";
        params.put("integration_name", integ_name);
        params.put("entity_type", entity_type);
        params.put("entity_id", id.get());
        ExtractableResponse<Response> response = new CbClient().doHttpGet(basePath, params);
        return response.jsonPath().getString("third_party_entity_mapping.third_party_entity_id");
    }

}
