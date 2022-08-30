package com.cbee.models;

import java.math.BigDecimal;
import java.util.Objects;

public class Money {
    public final Currency currency;
    public final BigDecimal value;

    public Money(Currency currency, Integer value) {
        this(currency, new BigDecimal(value));
    }

    public Money(Currency currency, BigDecimal value) {
        this.currency = currency;
        this.value = value;
    }

    public Money add(Money moneyToAdd) {
        if (this.currency != moneyToAdd.currency) {
            throw new IllegalArgumentException("Adding money across currencies not supported");
        }
        return new Money(this.currency, this.value.add(moneyToAdd.value));
    }

    public Money(Currency currency, float value) {
        this(currency, new BigDecimal(value));
    }

    public BigDecimal valueInLowestDenomination() {
        return currency == Currency.JPY ? value : value.multiply(new BigDecimal(100));
    }

    public static Money fromLowestDenomination(Currency currency, Integer value) {
        return currency == Currency.JPY ? new Money(currency, value) : new Money(currency, new Integer(value / 100));
    }

    public String toString() {
        String symbol = "";
        switch (currency) {
            case USD:
                symbol = "$";
                break;
            case EUR:
                symbol = "€";
                break;
            case JPY:
                symbol = "¥";
                break;
            case HKD:
                symbol = "$";
                break;
            case INR:
                symbol = "₹";
                break;
            case NZD:
                symbol = "$";
                break;
            case RUB:
                symbol = "₽";
                break;
            case AUD:
                symbol = "$";
                break;
            case CAD:
                symbol = "$";
                break;
            case GBP:
                symbol = "£";
                break;
            case SGD:
                symbol = "$";
                break;
        }
        return String.format("%s%s.00", symbol, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return currency == money.currency && value.equals(money.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, value);
    }
}
