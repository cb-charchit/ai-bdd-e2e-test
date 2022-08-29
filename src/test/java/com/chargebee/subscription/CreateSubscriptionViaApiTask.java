package com.chargebee.subscription;

import com.chargebee.Customer;
import com.chargebee.Site;
import com.chargebee.models.Subscription;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;

import java.util.Optional;

import static com.chargebee.ActorState.setTheNewlyCreatedSubscriptionInTheSpotlight;

public class CreateSubscriptionViaApiTask implements CreateSubscriptionTask {
    private Site site;

    public CreateSubscriptionViaApiTask(Site site) {
        this.site = site;
    }

    @Override
    public Performable createSubscription(CreateSubscriptionRequest createSubscriptionRequest, Optional<Customer> customer) {
        if (!customer.isPresent()) {
            throw new IllegalArgumentException("Customer not provided");
        }
        return Task.where("{0} attempts to create subscription via API", actor -> {
            try {
                Subscription subscription = site.createSubscription(createSubscriptionRequest, customer.get().getId().get());
                setTheNewlyCreatedSubscriptionInTheSpotlight(subscription);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
