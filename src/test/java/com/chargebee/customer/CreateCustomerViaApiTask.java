package com.chargebee.customer;

import com.chargebee.Customer;
import com.chargebee.Site;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import static com.chargebee.ActorState.setTheCustomerInTheSpotLight;

public class CreateCustomerViaApiTask implements CreateCustomerTask {
    private Site site;

    public CreateCustomerViaApiTask(Site site) {
        this.site = site;
    }

    @Override
    public Performable createCustomer(CreateCustomerTaskRequest createCustomerTaskRequest) {
        return Task.where("{0} attempts to create customer via API", actor -> {
            try {
                Customer customer =
                        site.createCustomer(createCustomerTaskRequest.getEmail().get(),
                                createCustomerTaskRequest.getFirstName().get(),
                                createCustomerTaskRequest.getCard().get());
                setTheCustomerInTheSpotLight(customer);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
