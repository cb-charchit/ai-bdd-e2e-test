package com.cbee.models;

import com.chargebee.models.Subscription;

public enum BillingFrequency {
    DAILY("Daily"),
    WEEKLY("Weekly"),
    MONTHLY("Monthly"),
    YEARLY("Yearly"),
    EVERY_THREE_MONTHS("Every 3 Months");

    private String displayName;

    BillingFrequency(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int billingPeriod() {
        switch (this) {
            case DAILY:
            case WEEKLY:
            case YEARLY:
            case MONTHLY:
                return 1;
            case EVERY_THREE_MONTHS:
                return 3;
        }
        throw new IllegalArgumentException("Invalid billing period : " + this);
    }

    public Subscription.BillingPeriodUnit billingPeriodUnit() {
        switch (this) {
            case DAILY:
                return Subscription.BillingPeriodUnit.DAY;
            case WEEKLY:
                return Subscription.BillingPeriodUnit.WEEK;
            case YEARLY:
                return Subscription.BillingPeriodUnit.YEAR;
            case MONTHLY:
            case EVERY_THREE_MONTHS:
                return Subscription.BillingPeriodUnit.MONTH;
        }
        throw new IllegalArgumentException("Invalid billing period : " + this);
    }
}
