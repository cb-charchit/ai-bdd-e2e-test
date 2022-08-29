package com.chargebee.subscription;

import com.chargebee.models.Quote;

import java.util.Map;
import java.util.Optional;

public class CreateNewSubscriptionFromQuoteRequest {

    public final Quote quote;
    public final AutoCollectionMode autoCollectionMode;
    public final InvoiceGenerationMode invoiceGenerationMode;
    private Optional<String> subscriptionId;
    private Optional<String> poNumber;

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = Optional.of(subscriptionId);
    }

    public CreateNewSubscriptionFromQuoteRequest(Quote quote, AutoCollectionMode autoCollectionMode,
                                                 InvoiceGenerationMode invoiceGenerationMode) {
        this.invoiceGenerationMode = invoiceGenerationMode;
        this.subscriptionId = Optional.empty();
        this.autoCollectionMode = autoCollectionMode;
        this.poNumber = Optional.empty();
        this.quote = quote;
    }

    public static CreateNewSubscriptionFromQuoteRequest fromMap(Quote quote, Map<String, String> requestMap) {

        AutoCollectionMode autoCollectionMode = AutoCollectionMode.USE_CUSTOMER_AUTO_COLLECTION_MODE;
        if (requestMap.containsKey("subscription_billing_auto_collection_mode")) {
            autoCollectionMode = AutoCollectionMode.valueOf(requestMap.get("subscription_billing_auto_collection_mode"));
        }

        InvoiceGenerationMode invoiceGenerationMode = InvoiceGenerationMode.IMMEDIATELY;
        if (requestMap.containsKey("subscription_invoicing_invoice_generation_mode")) {
            invoiceGenerationMode = InvoiceGenerationMode.valueOf(requestMap.get("subscription_invoicing_invoice_generation_mode"));
        }

        CreateNewSubscriptionFromQuoteRequest request = new CreateNewSubscriptionFromQuoteRequest(quote, autoCollectionMode, invoiceGenerationMode);

        if (requestMap.containsKey("subscription_id") && requestMap.get("subscription_id") != null) {
            request.setSubscriptionId(requestMap.get("subscription_id") + "_" + System.currentTimeMillis());
        }

        if (requestMap.containsKey("subscription_billing_po_number") && requestMap.get("subscription_billing_po_number") != null) {
            request.setPoNumber(requestMap.get("subscription_billing_po_number"));
        }

        return request;
    }

    public Optional<String> getSubscriptionId() {
        return subscriptionId;
    }

    public Optional<String> getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = Optional.of(poNumber);
    }

}
