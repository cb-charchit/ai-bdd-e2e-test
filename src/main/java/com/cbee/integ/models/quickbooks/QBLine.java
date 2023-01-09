package com.cbee.integ.models.quickbooks;

import org.json.JSONObject;

public class QBLine {

    private JSONObject qbLineJson;
    private QBLineDetail qbLineDetail;

    public QBLine(JSONObject qbLineJson) {
        this.qbLineJson = qbLineJson;
        this.qbLineDetail = qbLineJson != null ?
                new QBLineDetail(qbLineJson.optJSONObject(this.detailType())) : null;
    }

    public long id() {
        return qbLineJson.optLong("Id");
    }

    public long lineNum() {
        return qbLineJson.optLong("LineNum");
    }

    public String description() {
        return qbLineJson.optString("Description");
    }

    public long amount() {
        return qbLineJson.optLong("Amount");
    }

    public String detailType() {
        return qbLineJson.optString("DetailType");
    }

    public QBLineDetail qbLineDetail() {
        return qbLineDetail;
    }

    public class QBLineDetail {

        private JSONObject qbLineDetailJson;

        public QBLineDetail(JSONObject qbLineDetailJson) {
            this.qbLineDetailJson = qbLineDetailJson;
        }

        public JSONObject lineItemRef() {
            return qbLineDetailJson.optJSONObject("ItemRef");
        }

        public JSONObject lineItemAccountRef() {
            return qbLineDetailJson.optJSONObject("ItemAccountRef");
        }

        public JSONObject lineTaxCodeRef() {
            return qbLineDetailJson.optJSONObject("TaxCodeRef");
        }

        public JSONObject lineTaxClassificationRef() {
            return qbLineDetailJson.optJSONObject("TaxClassificationRef");
        }

        public int lineItemQuantity() {
            return qbLineDetailJson.optInt("Qty");
        }
    }
}
