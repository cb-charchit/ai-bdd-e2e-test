package com.chargebee.subscription;

import com.chargebee.Customer;
import net.serenitybdd.screenplay.Performable;

import java.util.Optional;

public interface CreateSubscriptionTask {
    Performable createSubscription(CreateSubscriptionRequest createSubscriptionRequest, Optional<Customer> customer);
}
