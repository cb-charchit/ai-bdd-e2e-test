package com.cbee.models;

public class Card implements PaymentMethod {
    public final com.chargebee.models.Card.CardType cardType;
    public final String number;
    public final String month;
    public final String year;
    public final String cvv;
    public final String last4Digits;

    public Card(com.chargebee.models.Card.CardType cardType, String number, String month, String year, String cvv) {
        this.cardType = cardType;
        this.number = number;
        this.month = month;
        this.year = year;
        this.cvv = cvv;
        last4Digits = number.substring(number.length() - 4);
    }

    public static Card fromCbCard(com.chargebee.models.Card cbCard) {
        return new Card(cbCard.cardType(), cbCard.maskedNumber(), cbCard.expiryMonth().toString(), cbCard.expiryYear().toString(), null);
    }

    public String toString() {
        return String.format("%s ending %s", cardType, last4Digits);
    }
}
