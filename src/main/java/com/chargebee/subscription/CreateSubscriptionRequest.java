package com.chargebee.subscription;

import com.chargebee.*;

import java.util.*;
import java.util.stream.Collectors;

public class CreateSubscriptionRequest {
    public final BillingStartDate billingStartDate;
    public final PricePoint planPricePoint;
    public final List<PricePoint> addonPricePoints;
    public final List<PricePoint> chargePricePoints;
    public final BillingCycle billingCycle;
    public final AutoCollectionMode autoCollectionMode;
    public final InvoiceGenerationMode invoiceGenerationMode;

    private Optional<String> subscriptionId;
    private Optional<String> poNumber;
    public List<String> coupons;

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = Optional.of(subscriptionId);
    }

    public CreateSubscriptionRequest(List<PricePoint> pricePoints) {
        this(pricePoints, new BillingStartDateImmediate(), new ForeverBillingCycle(),
                AutoCollectionMode.OFF, InvoiceGenerationMode.IMMEDIATELY);
    }

    public CreateSubscriptionRequest(List<PricePoint> pricePoints, BillingStartDate billingStartDate,
                                     BillingCycle billingCycle, AutoCollectionMode autoCollectionMode,
                                     InvoiceGenerationMode invoiceGenerationMode) {
        this.billingStartDate = billingStartDate;
        this.billingCycle = billingCycle;
        this.invoiceGenerationMode = invoiceGenerationMode;
        this.subscriptionId = Optional.empty();
        this.autoCollectionMode = autoCollectionMode;
        this.poNumber = Optional.empty();
        this.coupons = new ArrayList<>();

        PricePoint planPricePoint = null;
        for (PricePoint pricePoint : pricePoints) {
            if (pricePoint.item instanceof Plan || pricePoint.item instanceof MeteredPlan) {
                planPricePoint = pricePoint;
                break;
            }
        }
        if (planPricePoint == null) {
            throw new IllegalArgumentException("Create Subscription Request should contain a plan price point");
        }
        this.planPricePoint = planPricePoint;
        this.addonPricePoints = pricePoints.stream().filter(pricePoint -> pricePoint.item instanceof Addon).collect(Collectors.toList());
        this.chargePricePoints = pricePoints.stream().filter(pricePoint -> pricePoint.item instanceof Charge).collect(Collectors.toList());
    }

    public static CreateSubscriptionRequest fromMap(ProductCatalog productCatalog, Map<String, String> requestMap) {
        if (!requestMap.containsKey("item_price_ids")) {
            throw new IllegalArgumentException("item_price_ids value not found");
        }
        Map<String, PricePoint> pricePointsMap = Arrays.stream(requestMap.get("item_price_ids").split(",")).map(String::trim)
                .map(productCatalog::getPricePoint)
                .collect(Collectors.toMap(pricePoint -> pricePoint.id, pricePoint -> pricePoint));

        for (String pricePointId : pricePointsMap.keySet()) {
            if (requestMap.containsKey(pricePointId + ".unit_price")) {
                pricePointsMap.get(pricePointId).setUnitPrice(Float.parseFloat(requestMap.get(pricePointId + ".unit_price")));
            }
            if (requestMap.containsKey(pricePointId + ".quantity")) {
                pricePointsMap.get(pricePointId).setQuantity(Float.parseFloat(requestMap.get(pricePointId + ".quantity")));
            }
        }

        List<PricePoint> pricePoints = new ArrayList<>(pricePointsMap.values());
        BillingStartDate billingStartDate = new BillingStartDateImmediate();
        if (requestMap.containsKey("subscription_billing_start_date_type") &&
                requestMap.get("subscription_billing_start_date_type").equals("custom") &&
            requestMap.containsKey("subscription_billing_start_date_period")) {
            billingStartDate = new BillingStartDateCustom(requestMap.get("subscription_billing_start_date_period"));
        }

        BillingCycle billingCycle = new ForeverBillingCycle();
        if (requestMap.containsKey("subscription_billing_cycle_type") &&
                requestMap.get("subscription_billing_cycle_type").equals("fixed") &&
                requestMap.containsKey("fixed_billing_cycle_count")) {
            billingCycle = new FixedBillingCycle(Integer.parseInt(requestMap.get("fixed_billing_cycle_count")));
        }

        AutoCollectionMode autoCollectionMode = AutoCollectionMode.OFF;
       /* if (requestMap.containsKey("subscription_billing_auto_collection_mode")) {
            autoCollectionMode = AutoCollectionMode.valueOf(requestMap.get("subscription_billing_auto_collection_mode"));
        }*/

        InvoiceGenerationMode invoiceGenerationMode = InvoiceGenerationMode.IMMEDIATELY;
        if (requestMap.containsKey("subscription_invoicing_invoice_generation_mode")) {
            invoiceGenerationMode = InvoiceGenerationMode.valueOf(requestMap.get("subscription_invoicing_invoice_generation_mode"));
        }

        CreateSubscriptionRequest request = new CreateSubscriptionRequest(pricePoints, billingStartDate, billingCycle, autoCollectionMode, invoiceGenerationMode);

        if (requestMap.containsKey("subscription_id") && requestMap.get("subscription_id") != null) {
            request.setSubscriptionId(requestMap.get("subscription_id") + "_" + System.currentTimeMillis());
        }

        if (requestMap.containsKey("subscription_billing_po_number") && requestMap.get("subscription_billing_po_number") != null) {
            request.setPoNumber(requestMap.get("subscription_billing_po_number"));
        }

        if (requestMap.containsKey("coupons") && requestMap.get("coupons") != null) {
            List<String> coupons = Arrays.stream(requestMap.get("coupons").split(",")).map(String::trim)
                    .collect(Collectors.toList());
            request.setCoupons(coupons);
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

    public List<String> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<String> coupons) {
        this.coupons = coupons;
    }

    public int subscriptionItemsCount() {
        return 1 + addonPricePoints.size() + chargePricePoints.size();
    }
}
