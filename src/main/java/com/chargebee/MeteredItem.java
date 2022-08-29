package com.chargebee;

public abstract class MeteredItem extends Item {
    public final MeteredBillingMode meteredBillingMode;

    protected MeteredItem(String id, String name, ItemFamily itemFamily) {
        this(id, name, itemFamily, MeteredBillingMode.SUM_OF_ALL_USAGES);
    }

    protected MeteredItem(String id, String name, ItemFamily itemFamily, MeteredBillingMode meteredBillingMode) {
        super(id, name, itemFamily);
        this.meteredBillingMode = meteredBillingMode;
    }
}
