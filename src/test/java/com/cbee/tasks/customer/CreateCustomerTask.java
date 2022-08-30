package com.cbee.tasks.customer;

import net.serenitybdd.screenplay.Performable;

public interface CreateCustomerTask {
    Performable createCustomer(CreateCustomerTaskRequest createCustomerTaskRequest);
}
