package com.cbee.models;

public class MeteredPlan extends MeteredItem {
    public final Plan plan;

    public MeteredPlan(Plan plan) {
        super(plan.id, plan.name, plan.itemFamily, MeteredBillingMode.SUM_OF_ALL_USAGES);
        this.plan = plan;
    }

    public MeteredPlan(Plan plan, MeteredBillingMode meteredBillingMode) {
        super(plan.id, plan.name, plan.itemFamily, meteredBillingMode);
        this.plan = plan;
    }
}
