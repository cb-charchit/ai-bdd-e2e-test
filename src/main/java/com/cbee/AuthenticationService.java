package com.cbee;

import com.cbee.api.FetchThirdPartyDetails;
import com.cbee.clients.QBOClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class AuthenticationService implements Authentication {

    private Token token;

    private final String accessToken = "third_party_configuration.auth_json.access_token";
    private final String refreshToken = "third_party_configuration.auth_json.refresh_token";
    private final String expiresIn = "third_party_configuration.auth_json.expires_in";
    private final String xRefreshTokenExpiresIn = "third_party_configuration.auth_json.x_refresh_token_expires_in";
    private final String authType = "third_party_configuration.auth_json.authType";
    private final String tokenType = "third_party_configuration.auth_json.token_type";


    @Override
    public Token getToken(String integ_name) {
        ExtractableResponse<Response> response = FetchThirdPartyDetails.getTpIntegConfigs(integ_name);
        token = new Token(response.jsonPath().getString(accessToken),
                response.jsonPath().getString(refreshToken),
                response.jsonPath().getString(expiresIn),
                response.jsonPath().getString(xRefreshTokenExpiresIn),
                response.jsonPath().getString(authType),
                response.jsonPath().getString(tokenType));
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
            token.setAccessToken(response.jsonPath().getString("access_token"));
            String authJson = convertToJSON(token);
            FetchThirdPartyDetails.updateTpIntegConfigs("quickbooks", authJson);
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
