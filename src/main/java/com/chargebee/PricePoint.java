package com.chargebee;

import java.util.Optional;

public class PricePoint {
    public final String id;
    public final PriceModel priceModel;
    public final Item item;
    public final Optional<BillingFrequency> billingFrequency;

    private Optional<TrialPeriod> trialPeriod = Optional.empty();
    private Optional<Float> quantity = Optional.empty();
    private Optional<Float> unitPrice = Optional.empty();

    public PricePoint(String id, Item item, PriceModel priceModel, BillingFrequency billingFrequency) {
        this.id = id;
        this.priceModel = priceModel;
        this.item = item;
        if (item instanceof MeteredItem && priceModel instanceof FlatFee) {
           throw new IllegalArgumentException("Flat fee not supported metered item");
        }
        this.billingFrequency = billingFrequency != null ? Optional.of(billingFrequency) : Optional.empty();
    }

    public PricePoint(String id, Item item, PriceModel priceModel) {
        this(id, item, priceModel, null);
    }

    public String itemName() {
        return item.name;
    }

    public String itemFamilyName() {return item.itemFamily.name;}

    public Money price() {
        return priceModel.price();
    }

    public Money price(int usageQuantity) {
        return priceModel.usagePriceFor(usageQuantity);
    }

    public void setTrialPeriod(TrialPeriod trialPeriod) {
        this.trialPeriod = Optional.of(trialPeriod);
    }

    public Optional<TrialPeriod> getTrialPeriod() {
        return trialPeriod;
    }

    public Optional<Float> getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = Optional.of(quantity);
    }

    public Optional<Float> getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = Optional.of(unitPrice);
    }

    public boolean hasQuantity() {
        return this.quantity.isPresent();
    }

    public Optional<Boolean> isQuantityInInteger() {
        return quantity.map(qty -> qty == Math.ceil(qty));
    }

    public Optional<Boolean> isQuantityInFloat() {
        return this.isQuantityInInteger().map(result -> !result);
    }

    public Optional<String> quantityAsString() {
        return this.isQuantityInFloat().flatMap(isFloat -> isFloat ? this.quantity.map(qty -> "") : this.quantity.map(qty -> "" + qty.intValue()));
    }
}
