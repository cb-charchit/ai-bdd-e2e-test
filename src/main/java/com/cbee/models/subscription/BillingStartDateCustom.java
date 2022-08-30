package com.cbee.models.subscription;

import java.time.Period;

public class BillingStartDateCustom extends BillingStartDate{
    public final Period period;

    public BillingStartDateCustom(String periodString) {
        this(Period.parse(periodString));
    }

    public BillingStartDateCustom(Period period) {
        this.period = period;
    }
}
