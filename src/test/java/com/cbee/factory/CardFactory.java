package com.cbee.factory;

import com.cbee.models.Card;

public class CardFactory {
    public static Card validVisaCard() {
        return new Card(com.chargebee.models.Card.CardType.VISA, "4111111111111111", "12", "2023", "100");
    }

    public static Card validStripeVisaCard() {
        return new Card(com.chargebee.models.Card.CardType.VISA, "4242424242424242", "12", "2049", "123");
    }

    public static Card get(String cardString) {
        switch (cardString) {
            case "valid_visa_card":
                return validVisaCard();
            case "valid_stripe_visa_card":
                return validStripeVisaCard();
        }
        throw new IllegalArgumentException(cardString + " not supported");
    }
}
