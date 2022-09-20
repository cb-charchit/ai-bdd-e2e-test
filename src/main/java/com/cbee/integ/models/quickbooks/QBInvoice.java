package com.cbee.integ.models.quickbooks;

import org.json.JSONObject;

public class QBInvoice {

    private JSONObject qbInvoiceJson;

    public QBInvoice(JSONObject qbInvoiceJson) {
        this.qbInvoiceJson = qbInvoiceJson;
    }

    public long id() {
        return qbInvoiceJson.optLong("Id");
    }

    public long totalAmount() {
        return qbInvoiceJson.optLong("TotalAmt");
    }

    public JSONObject customerRef() {
        return qbInvoiceJson.optJSONObject("CustomerRef");
    }

    public JSONObject billingAddress() {
        return qbInvoiceJson.optJSONObject("BillAddr");
    }
}
