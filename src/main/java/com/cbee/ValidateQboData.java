package com.cbee;

import com.cbee.clients.QBOClient;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class ValidateQboData {

    public static void main(String[] args) {
        try {
            checkIfRecordIsPresentInQBO("quickbooks", "invoice", "821");
        } catch (Exception e) {
            System.out.println("Something went wrong : " + e);
        }
    }

    public static void checkIfRecordIsPresentInQBO(String integ_name, String entity_type, String id) throws Exception {
        Authentication authService = new AuthenticationService();
        QBOClient qbClient = new QBOClient();
        Token t = authService.getTheToken("quickbooks");

        String basePath = "v3/company/4620816365172822790/invoice/821";
        Map<String, String> params = new HashMap<>();
        params.put("minorversion", "65");

        ExtractableResponse<Response> response = qbClient.doHttpGet(basePath, params, t.getAccess_token());

        if (response.statusCode() == 401) {
            authService.refreshTheToken();
            ExtractableResponse<Response> response1 = qbClient.doHttpGet(basePath, params, t.getAccess_token());
            System.out.println(response1.statusCode());
        }
    }
}
