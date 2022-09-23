package com.cbee.api;

import com.cbee.Authentication;
import com.cbee.AuthenticationService;
import com.cbee.Token;
import com.cbee.clients.QBOClient;
import com.cbee.integ.models.quickbooks.QBInvoice;
import com.cbee.utils.ConfigFileReader;
import com.google.gson.Gson;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class FetchEntitiesFromQuickbooks {

    private static final String QUICKBOOKS = "quickbooks";
    private Authentication authService;
    private QBOClient qbClient;
    private Token token;
    private String companyId;
    private String basePath;

    public FetchEntitiesFromQuickbooks() {
        authService = new AuthenticationService();
        qbClient = new QBOClient();
        token = authService.getTheToken(QUICKBOOKS);
        companyId = new ConfigFileReader().getConfigValueByKey("qbCompanyId");
        basePath = "v3/company/"+companyId;
    }

    public QBInvoice fetchQuickbooksInvoiceByQBInvId(String qbInvId) {
        String path = basePath + "/invoice/" + qbInvId;
        Map<String, String> params = new HashMap<>();
        ExtractableResponse<Response> response = qbClient.doHttpGet(path, params, token.getAccess_token());
        if (response.statusCode() == 401) {
            authService.refreshTheToken();
        }
        response = qbClient.doHttpGet(path, params, token.getAccess_token());
        Gson gson = new Gson();
        String json = gson.toJson(response.jsonPath().getJsonObject("Invoice"), LinkedHashMap.class);
        JSONObject jsonObject = new JSONObject(json);
        return new QBInvoice(jsonObject);
    }
}
