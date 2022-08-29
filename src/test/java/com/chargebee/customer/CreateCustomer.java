package com.chargebee.customer;

import com.chargebee.*;
import com.factory.CardFactory;
import net.serenitybdd.screenplay.Performable;


import java.util.Optional;

public class CreateCustomer {

    public final CreateCustomerTaskRequest createCustomerTaskRequest;
    private Site site;

    public CreateCustomer(CreateCustomerTaskRequest createCustomerTaskRequest) {
        this.createCustomerTaskRequest = createCustomerTaskRequest;
    }
    public static CreateCustomer using(String firstName, String email, String cardDetail, String... businessEntity) {
        String randomizedEmail = email.replace("@example.com", "+" + System.currentTimeMillis() + "@example.com");
        if (businessEntity.length>0){
            return new CreateCustomer(CreateCustomer.with(randomizedEmail, firstName, CardFactory.get(cardDetail),businessEntity[0]));
        }
        else{
            return new CreateCustomer(CreateCustomer.with(randomizedEmail, firstName,CardFactory.get(cardDetail)));

        }
    }

    public static CreateCustomerTaskRequest with(String email, String firstName,Card cardDetail) {
        return with(email, firstName, cardDetail,null);
    }

    public static CreateCustomerTaskRequest with(String email, String firstName,  Card cardDetail,String businessEntityName) {
        CreateCustomerTaskRequest createCustomerTaskRequest = new CreateCustomerTaskRequest();
        createCustomerTaskRequest.setEmail(email);
        createCustomerTaskRequest.setFirstName(firstName);
        createCustomerTaskRequest.setCard(cardDetail);
        if(businessEntityName != null){
            createCustomerTaskRequest.setBusinessEntityName(Optional.of(businessEntityName));
        }
        return createCustomerTaskRequest;
    }

    public Performable via(InteractionMode interactionMode) {
        return createCustomerTaskRequest.via(theCreateCustomer(interactionMode));
    }

    public CreateCustomer on(Site site) {
        this.site = site;
        return this;
    }

    private CreateCustomerTask theCreateCustomer(InteractionMode interactionMode) {
        if (interactionMode instanceof InteractionModeApi) {
            return new CreateCustomerViaApiTask(site);
        }
        throw new IllegalArgumentException("Invalid interaction mode " + interactionMode);
    }

}
