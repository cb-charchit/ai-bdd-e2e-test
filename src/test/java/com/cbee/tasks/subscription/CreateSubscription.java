package com.cbee.tasks.subscription;

import com.cbee.models.*;
import com.cbee.models.subscription.CreateSubscriptionRequest;
import net.serenitybdd.screenplay.Performable;

import java.util.Optional;

public class CreateSubscription {
    private CreateSubscriptionRequest createSubscriptionRequest;
    private Customer customer;
    private Site site;

    public CreateSubscription(CreateSubscriptionRequest createSubscriptionRequest) {
        this.createSubscriptionRequest = createSubscriptionRequest;
    }

    public static CreateSubscriptionTaskRequest with(CreateSubscriptionRequest createSubscriptionRequest) {
        return new CreateSubscriptionTaskRequest(createSubscriptionRequest);
    }

    public static CreateSubscription using(CreateSubscriptionRequest createSubscriptionRequest) {
        return new CreateSubscription(createSubscriptionRequest);
    }

    public CreateSubscription forThe(Customer customer) {
        this.customer = customer;
        return this;
    }

    public CreateSubscription on(Site site) {
        this.site = site;
        return this;
    }

    public Performable via(InteractionMode interactionMode) {
        CreateSubscriptionTask createSubscriptionTask = null;
        if (interactionMode instanceof InteractionModeApi) {
            createSubscriptionTask = new CreateSubscriptionViaApiTask(site);
        }
        if (interactionMode instanceof InteractionModeLeapUI) {
            InteractionModeLeapUI interactionModeLeapUI = (InteractionModeLeapUI) interactionMode;
            createSubscriptionTask = new CreateSubscriptionViaLeapUITask(site, interactionModeLeapUI.user);
        }
        if (createSubscriptionTask == null) {
            throw new IllegalArgumentException("Invalid interaction mode " + interactionMode);
        }
        return createSubscriptionTask.createSubscription(createSubscriptionRequest, Optional.ofNullable(customer));
    }
}
