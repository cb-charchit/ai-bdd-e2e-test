package com.chargebee;

public class MeteredAddon extends MeteredItem {
    public final Addon addon;

    public MeteredAddon(Addon addon, MeteredBillingMode meteredBillingMode) {
        super(addon.id, addon.name, addon.itemFamily, meteredBillingMode);
        this.addon = addon;
    }
}
