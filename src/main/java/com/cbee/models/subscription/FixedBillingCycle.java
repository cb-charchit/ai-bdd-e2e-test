package com.cbee.models.subscription;

public class FixedBillingCycle extends BillingCycle{
    public final int billingCycleCount;

    public FixedBillingCycle(int billingCycleCount) {
        this.billingCycleCount = billingCycleCount;
    }
}
