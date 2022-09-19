package com.cbee;

import com.cbee.clients.QBOClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.io.IOException;

public class AuthenticationService implements Authentication {

    private Token token;
    private TpIntegConfig tpIntegConfig;

    private final String access_token = "third_party_configuration.auth_json.access_token";
    private final String refresh_token = "third_party_configuration.auth_json.refresh_token";
    private final String expires_in = "third_party_configuration.auth_json.expires_in";
    private final String x_refresh_token_expires_in = "third_party_configuration.auth_json.x_refresh_token_expires_in";
    private final String authType = "third_party_configuration.auth_json.authType";
    private final String token_type = "third_party_configuration.auth_json.token_type";


    @Override
    public Token getTheToken(String integ_name) {
        tpIntegConfig = new TpIntegConfig();
        ExtractableResponse<Response> response = tpIntegConfig.getTpIntegConfigs(integ_name);
        token = new Token(response.jsonPath().getString(access_token),
                response.jsonPath().getString(refresh_token),
                response.jsonPath().getString(expires_in),
                response.jsonPath().getString(x_refresh_token_expires_in),
                response.jsonPath().getString(authType),
                response.jsonPath().getString(token_type));
        return token;
    }

    @Override
    public void refreshTheToken() {
        try {
            ExtractableResponse<Response> response = new QBOClient().doHttpPost(token);
            if(response.statusCode() == 400 )
            { //Todo - call reconnect functionality here
                System.out.println("GO to UI and reset refresh token");
                return;
            }
            token.setAccess_token(response.jsonPath().getString("access_token"));

            tpIntegConfig = new TpIntegConfig();
            String authJson = convertToJSON(token);
            tpIntegConfig.updateTpIntegConfigs("quickbooks", authJson);
        } catch (Exception e) {
            System.out.println("Something went wrong while calling QBO" + e);
        }

    }

    //Move to some util
    public String convertToJSON(Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            System.out.println("Sorry something went wrong" + e);
        }
        return null;
    }
}
