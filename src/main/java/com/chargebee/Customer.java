package com.chargebee;

import java.util.Optional;

public class Customer {
    private Optional<String> firstName;
    private Optional<String> email;
    private Optional<String> company;
    private Optional<Address> billingAddress;
    private Optional<PaymentMethod> paymentMethod;
    private Optional<String> id;
    private Optional<String> businessEntityName;
    private Optional<String> businessEntityId;//To store Multi-business entity related data
    private Optional<String> preferredCurrencyCode;

    public Customer() {
        company = Optional.empty();
        firstName = Optional.empty();
        email = Optional.empty();
        billingAddress = Optional.empty();
        id = Optional.empty();
        paymentMethod = Optional.empty();
        preferredCurrencyCode = Optional.empty();
    }

    public static Customer fromCbCustomer(com.chargebee.models.Customer customer, Card card) {
        if (card!=null){
        Customer customer_ = fromCbCustomer(customer, (com.chargebee.models.Card) null);
        customer_.setPaymentMethod(card);
        return customer_;
        }
        return fromCbCustomer(customer, (com.chargebee.models.Card) null);
    }

    public static Customer fromCbCustomer(com.chargebee.models.Customer customer) {
        return fromCbCustomer(customer, (com.chargebee.models.Card) null);
    }

    public void setBillingAddress(Address address) {
        billingAddress = Optional.of(address);
    }

    public Optional<Address> getBillingAddress() {
        return billingAddress;
    }

    public static Customer fromCbCustomer(com.chargebee.models.Customer cbCustomer, com.chargebee.models.Card card) {
        Customer customer = new Customer();
        customer.setId(cbCustomer.id());
        customer.setFirstName(cbCustomer.firstName());
        customer.setEmail(cbCustomer.email());
        customer.setCompany(cbCustomer.company());
        customer.setPreferredCurrencyCode(cbCustomer.preferredCurrencyCode());
        if (cbCustomer.billingAddress() != null) {
            customer.setBillingAddress(Address.fromCbCustomerBillingAddress(cbCustomer.billingAddress()));
        }
        //To set business entity id retrieved from get customer API response
        if (cbCustomer.businessEntityId() != null) {
            customer.setBusinessEntityId(cbCustomer.businessEntityId());
        }
        if (card != null) {
            customer.setPaymentMethod(Card.fromCbCard(card));
        }
        return customer;
    }

    private void setPreferredCurrencyCode(String preferredCurrencyCode) {
        if (preferredCurrencyCode != null) {
            this.preferredCurrencyCode = Optional.of(preferredCurrencyCode);
        }
    }

    public void setFirstName(String firstName) {
        if (firstName != null) {
            this.firstName = Optional.of(firstName);
        }
    }
    public Optional<String> getFirstName() {
        return this.firstName;
    }

    public void setEmail(String email) {
        if (email != null) {
            this.email = Optional.of(email);
        }
    }
    public Optional<String> getEmail() {
        return this.email;
    }

    public static String randomizeEmail(String email) {
        return email.replace("@example.com", "+" + System.currentTimeMillis() + "@example.com");
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", billingAddress=" + billingAddress +
                '}';
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = Optional.of(paymentMethod);
    }

    public Optional<PaymentMethod> getPaymentMethod() {
        return paymentMethod;
    }

    public Optional<String> getCompany() {
        return company;
    }

    public Optional<String> getPreferredCurrencyCode() {
        return preferredCurrencyCode;
    }

    public void setCompany(String company) {
        if (company != null) {
            this.company = Optional.of(company);
        }
    }

    public Optional<String> getId() {
        return id;
    }

    public void setId(String id) {
        this.id = Optional.of(id);
    }

    public void setRandomizedEmail(String email) {
        setEmail(randomizeEmail(email));
    }

    /**
     * To set the business entity name
     * @param businessEntityName - business entity name
     */
    public void setBusinessEntityName(String businessEntityName) {
        if (businessEntityName != null) {
            this.businessEntityName = Optional.of(businessEntityName);
        }
    }

    /**
     * To retrieve stored the business entity name
     * @return - business entity name
     */
    public Optional<String> getBusinessEntityName() {
        return businessEntityName;
    }

    /**
     * To set business entity Id
     * @param businessEntityId - business entity id
     */
    public void setBusinessEntityId(String businessEntityId) {
        if (businessEntityId != null) {
            this.businessEntityId = Optional.of(businessEntityId);
        }
    }

    /**
     * To retrieve stored business entity id
     * @return - business entity id
     */
    public Optional<String> getBusinessEntityId() {
        return businessEntityId;
    }
}
