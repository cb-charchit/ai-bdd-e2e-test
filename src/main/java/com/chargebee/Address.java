package com.chargebee;

import com.chargebee.models.Customer;

public class Address {
    public final String firstName;
    public final String lastName;
    public final String line1;
    public final String city;
    public final String state;
    public final String countryDisplayName;
    public final String countryInternalName;
    public final String zip;

    public Address(String firstName, String lastName, String line1, String city, String state, String countryDisplayName, String countryInternalName, String zip) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.line1 = line1;
        this.city = city;
        this.state = state;
        this.countryDisplayName = countryDisplayName;
        this.countryInternalName = countryInternalName;
        this.zip = zip;
    }

    public static Address fromCbCustomerBillingAddress(Customer.BillingAddress billingAddress) {
        return new Address(billingAddress.firstName(), billingAddress.lastName(), billingAddress.line1(),
                billingAddress.city(), billingAddress.state(), billingAddress.country(), billingAddress.country(), billingAddress.zip());
    }

    @Override
    public String toString() {
        return "Address{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", line1='" + line1 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", countryDisplayName='" + countryDisplayName + '\'' +
                ", countryInternalName='" + countryInternalName + '\'' +
                ", zip='" + zip + '\'' +
                '}';
    }
}
