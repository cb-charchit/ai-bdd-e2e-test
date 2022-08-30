package com.cbee.models.subscription;

public enum AutoCollectionMode {
    USE_CUSTOMER_AUTO_COLLECTION_MODE("Use Customer setting"),
    ON("On"),
    OFF("Off");

    private String displayName;

    AutoCollectionMode(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
