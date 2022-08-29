package com.chargebee;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Cart {
    public final List<CartItem> cartItems;
    private Optional<String> ponumber = Optional.empty();

    public Cart(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public static Cart fromMaps(ProductCatalog productCatalog, List<Map<String,String>> rows) {
        ArrayList<CartItem> cartItems = new ArrayList<>();
        rows.forEach(row -> {
            cartItems.add(new CartItem(productCatalog.getPricePoint(row.get("item_price_id"))));
        });
        return new Cart(cartItems);
    }

    public Optional<String> getPonumber() {
        return ponumber;
    }

    public void setPonumber(String ponumber) {
        this.ponumber = Optional.of(ponumber);
    }
}
