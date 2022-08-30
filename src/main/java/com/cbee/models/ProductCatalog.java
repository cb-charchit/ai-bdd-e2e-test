package com.cbee.models;

import java.util.HashMap;

public class ProductCatalog {
    private final HashMap<String, PricePoint> pricePoints;

    public ProductCatalog(HashMap<String, PricePoint> pricePoints) {
        this.pricePoints = pricePoints;
    }

    public String getItemFamilyName(String pricePointId) {
        return pricePoints.get(pricePointId).itemFamilyName();
    }

    public PricePoint getPricePoint(String pricePointId) {
        return pricePoints.get(pricePointId);
    }
}
