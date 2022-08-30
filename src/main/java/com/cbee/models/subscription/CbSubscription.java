package com.cbee.models.subscription;

import com.chargebee.models.Subscription;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CbSubscription {
    private final Subscription subscription;

    public CbSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public Subscription.SubscriptionItem planItem() {
        return subscription.subscriptionItems().get(0);
    }

    public List<Subscription.SubscriptionItem> addonItems() {
        return subscription.subscriptionItems().stream().filter(subscriptionItem -> subscriptionItem.itemType().equals(Subscription.SubscriptionItem.ItemType.ADDON)).collect(Collectors.toList());
    }

    public List<Subscription.SubscriptionItem> chargeItems() {
        return subscription.subscriptionItems().stream().filter(subscriptionItem -> subscriptionItem.itemType().equals(Subscription.SubscriptionItem.ItemType.CHARGE)).collect(Collectors.toList());
    }

    public int itemsCount() {
        return subscription.subscriptionItems().size();
    }

    public String planPricePointId() {
        return planItem().itemPriceId();
    }

    public int billingPeriod() {
        return subscription.billingPeriod();
    }

    public Subscription.BillingPeriodUnit billingPeriodUnit() {
        return subscription.billingPeriodUnit();
    }

    public Optional<Subscription.Coupon> couponById(String couponId) {
        return subscription.coupons().stream().filter(coupon -> coupon.couponId() == couponId).findFirst();
    }

    public String id() {
        return subscription.id();
    }

    public String poNumber() {
        return subscription.poNumber();
    }

    public boolean hasMeteredBillingPlan() {
        try {
            planItem().quantity().equals(null);
            return false;
        }catch (Exception e){
            return true;
        }
    }

    public int couponsCount() {
        return subscription.coupons().size();
    }
}
