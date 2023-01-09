package com.cbee;

import com.cbee.models.Customer;
import com.cbee.models.Merchant;
import com.cbee.models.PaymentMethod;
import com.cbee.models.Site;
import com.cbee.models.subscription.CreateSubscriptionRequest;
import com.chargebee.models.Subscription;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class ActorState {

    private ActorState() {}

    public static void setTheNewlyCreatedCustomerInTheSpotLight(Customer customer) {
        theActorInTheSpotlight().remember("new_customer", customer);
    }
    public static Customer theNewlyCreatedCustomerInTheSpotlight() {
        return theActorInTheSpotlight().recall("new_customer");
    }

    public static void setTheCustomerInTheSpotLight(Customer customer) {
        theActorInTheSpotlight().remember("customer", customer);
    }
    public static Customer theCustomerInTheSpotlight() {
        return theActorInTheSpotlight().recall("customer");
    }

    public static void setThirdPartyIdInTheSpotLight(String thirdPartyId) {
        theActorInTheSpotlight().remember("thirdPartyId", thirdPartyId);
    }

    public static String thirdPartyIdInTheSpotLight() {
        return theActorInTheSpotlight().recall("thirdPartyId");
    }

    public static void setConfigInTheSpotLight(String configJson) {
        theActorInTheSpotlight().remember("configJson", configJson);
    }

    public static boolean configInTheSpotLight() {
        return theActorInTheSpotlight().recall("configJson");
    }

    public static void setThePaymentMethodInTheSpotlight(PaymentMethod paymentMethod) {
        theActorInTheSpotlight().remember("payment_method", paymentMethod);
    }
    public static PaymentMethod thePaymentMethodInTheSpotlight() {
        return theActorInTheSpotlight().recall("payment_method");
    }

    public static void setTheCreateSubscriptionRequestInTheSpotLight(CreateSubscriptionRequest createSubscriptionRequest) {
        theActorInTheSpotlight().remember("create_subscription_request", createSubscriptionRequest);
    }
    public static CreateSubscriptionRequest theCreateSubscriptionRequestInTheSpotLight() {
        return theActorInTheSpotlight().recall("create_subscription_request");
    }
    public static void setTheNewlyCreatedSubscriptionInTheSpotlight(Subscription subscription) {
        theActorInTheSpotlight().remember("new_subscription", subscription);
    }
    public static Subscription theNewlyCreatedSubscriptionInTheSpotlight() {
        return theActorInTheSpotlight().recall("new_subscription");
    }

    public static Site theTestSiteInTheSpotlight() {
        return theMerchantInTheSpotLight().testSite;
    }
    public static Merchant theMerchantInTheSpotLight() {
        return theActorInTheSpotlight().recall("merchant");
    }

}
