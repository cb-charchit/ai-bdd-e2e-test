package com.cbee.models;

import java.math.BigDecimal;
import java.util.Optional;

public class PricingSlab {
    public final Integer from;
    public final Optional<Integer> to;
    public final BigDecimal moneyValue;

    public PricingSlab(int from, int to, int moneyValue) {
        this(from, to, new BigDecimal(moneyValue));
    }

    public PricingSlab(int from, int to, BigDecimal moneyValue) {
        this.from = from;
        this.to = Optional.of(to);
        this.moneyValue = moneyValue;
    }

    public PricingSlab(int from, BigDecimal moneyValue) {
        this.from = from;
        this.to = Optional.empty();
        this.moneyValue = moneyValue;
    }

    public PricingSlab(int from, int moneyValue) {
        this(from, new BigDecimal(moneyValue));
    }
}
