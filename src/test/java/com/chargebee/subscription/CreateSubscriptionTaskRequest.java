package com.chargebee.subscription;

import com.chargebee.Customer;
import net.serenitybdd.screenplay.Performable;

import java.util.Optional;

public class CreateSubscriptionTaskRequest {
    public final CreateSubscriptionRequest createSubscriptionRequest;
    private Optional<Customer> customer = Optional.empty();

    public CreateSubscriptionTaskRequest(CreateSubscriptionRequest createSubscriptionRequest) {
        this.createSubscriptionRequest = createSubscriptionRequest;
    }

    public CreateSubscriptionTaskRequest forThe(Customer customer) {
        this.customer = Optional.of(customer);
        return this;
    }

    public Performable via(CreateSubscriptionTask createSubscriptionTask) {
        return createSubscriptionTask.createSubscription(createSubscriptionRequest, customer);
    }


    public Optional<Customer> getCustomer() {
        return customer;
    }
}
