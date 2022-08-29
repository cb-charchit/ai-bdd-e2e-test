package com.chargebee;

public abstract class Item {
    public final String id;
    public final String name;
    public final ItemFamily itemFamily;

    protected Item(String id, String name, ItemFamily itemFamily) {
        this.id = id;
        this.name = name;
        this.itemFamily = itemFamily;
    }
}
