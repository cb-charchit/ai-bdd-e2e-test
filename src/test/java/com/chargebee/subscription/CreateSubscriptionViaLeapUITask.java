package com.chargebee.subscription;

import com.chargebee.Customer;
import com.chargebee.Site;
import com.chargebee.User;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;

import java.util.Optional;

import static com.chargebee.ActorState.setTheNewlyCreatedSubscriptionInTheSpotlight;
import static com.chargebee.ActorState.theTestSiteInTheSpotlight;


public class CreateSubscriptionViaLeapUITask implements CreateSubscriptionTask {
    private Site site;
    private User user;

    public CreateSubscriptionViaLeapUITask(Site site, User user) {
        this.site = site;
        this.user = user;
    }

    @Override
    public Performable createSubscription(CreateSubscriptionRequest createSubscriptionRequest, Optional<Customer> customer) {
        if (!customer.flatMap(Customer::getEmail).isPresent()) {
            throw new IllegalArgumentException("Customer should present");
        }
        return Task.where("{0} attempts to create subscription via Leap UI"
               /* NavigateTo.subscriptionsIndexPage(),
                NavigateTo.createNewSubscriptionPage(customer.flatMap(Customer::getEmail).get()),
                CreateSubscriptionPage.populateSubscriptionInfo(createSubscriptionRequest),
                CreateSubscriptionPage.clickOnCreateSubscriptionButton(),
                captureNewlyCreatedSubscriptionDetails()*/
        );
    }

    private Performable captureNewlyCreatedSubscriptionDetails()  {
        return Task.where(actor -> {
          //  String subscriptionId = SubscriptionPage.subscriptionDetalPage.subscriptionId().answeredBy(actor);
            try {
               // setTheNewlyCreatedSubscriptionInTheSpotlight(theTestSiteInTheSpotlight().getSubscription(subscriptionId));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}