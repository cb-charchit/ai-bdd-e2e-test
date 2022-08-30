package com.cbee.models.subscription;

public enum InvoiceGenerationMode {
    IMMEDIATELY("Immediately"),
    ADD_TO_UNBILLED_CHARGES("Add to Unbilled Charges");

    private String displayName;

    InvoiceGenerationMode(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
