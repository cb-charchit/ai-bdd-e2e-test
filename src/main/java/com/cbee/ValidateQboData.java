package com.cbee;

import com.cbee.api.FetchEntitiesFromQuickbooks;
import com.cbee.clients.QBOClient;
import com.cbee.integ.models.quickbooks.QBInvoice;
import com.cbee.utils.ConfigFileReader;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class ValidateQboData {

    public static void checkIfRecordIsPresentInQBO(String integ_name, String entity_type, String id) {
        Authentication authService = new AuthenticationService();
        QBOClient qbClient = new QBOClient();
        Token t = authService.getTheToken(integ_name);
        String companyId= new ConfigFileReader().getConfigValueByKey("qbCompanyId");

        String basePath = "v3/company/"+companyId+"/"+entity_type+"/"+id;
        Map<String, String> params = new HashMap<>();
        params.put("minorversion", "65");

        ExtractableResponse<Response> response = qbClient.doHttpGet(basePath, params, t.getAccess_token());

        if (response.statusCode() == 401) {
            authService.refreshTheToken();
            ExtractableResponse<Response> response1 = qbClient.doHttpGet(basePath, params, t.getAccess_token());
            System.out.println(response1.statusCode());
        }
    }

    public static void validateSyncedInvoice(String qbInvId, String amount) {

        QBInvoice qbInvoice = new FetchEntitiesFromQuickbooks().fetchQuickbooksInvoiceByQBInvId(qbInvId);
        long syncedInvoiceAmount = qbInvoice.totalAmount();

    }
}
