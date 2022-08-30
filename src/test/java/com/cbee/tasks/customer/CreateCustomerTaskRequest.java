package com.cbee.tasks.customer;

import com.cbee.models.Card;
import net.serenitybdd.screenplay.Performable;

import java.util.Optional;

public class CreateCustomerTaskRequest {
    private Optional<String> firstName;
    private Optional<String> email;
    private Optional<Card> card;
    private Optional<String> businessEntityName;


    public Optional<String> getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = Optional.of(firstName);
    }

    public Optional<String> getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = Optional.of(email);
    }

    public Optional<Card> getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = Optional.of(card);
    }

    public Optional<String> getBusinessEntityName() {
        return businessEntityName;
    }

    public void setBusinessEntityName(Optional<String> businessEntityName) {
        this.businessEntityName = businessEntityName;
    }

    public Performable via(CreateCustomerTask createCustomerTask) {
        return createCustomerTask.createCustomer(this);
    }
}
