package com.cbee.integ.models.quickbooks;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class QBInvoice {

    private JSONObject qbInvoiceJson;
    private JSONArray lineJsonArray;

    public QBInvoice(JSONObject qbInvoiceJson) {
        this.qbInvoiceJson = qbInvoiceJson;
        lineJsonArray = this.qbInvoiceJson.optJSONArray("Line");
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

    public JSONObject shipFromAddress() {
        return qbInvoiceJson.optJSONObject("ShipFromAddr");
    }

    public String docNumber() {
        return qbInvoiceJson.optString("DocNumber");
    }

    public JSONObject currencyRef() {
        return qbInvoiceJson.optJSONObject("CurrencyRef");
    }

    public BigDecimal exchangeRate() {
        return qbInvoiceJson.optBigDecimal("ExchangeRate", BigDecimal.ONE);
    }

    public List<QBLine> qbLines() {
        List<QBLine> qbLines = new LinkedList<>();
        for(int i = 0; i < lineJsonArray.length()-1; i++) {
            QBLine qbLine = new QBLine((JSONObject) lineJsonArray.opt(i));
            qbLines.add(qbLine);
        }
        return qbLines;
    }

    public JSONObject subTotalLineDetail() {
        return (JSONObject) lineJsonArray.opt(lineJsonArray.length()-1);
    }
}
