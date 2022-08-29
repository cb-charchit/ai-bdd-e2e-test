package com.chargebee.customer;

import net.serenitybdd.screenplay.Performable;

public interface CreateCustomerTask {
    Performable createCustomer(CreateCustomerTaskRequest createCustomerTaskRequest);
}
