package com.cbee.tasks.subscription;

import com.cbee.models.Customer;
import com.cbee.models.subscription.CreateSubscriptionRequest;
import net.serenitybdd.screenplay.Performable;

import java.util.Optional;

public interface CreateSubscriptionTask {
    Performable createSubscription(CreateSubscriptionRequest createSubscriptionRequest, Optional<Customer> customer);
}
