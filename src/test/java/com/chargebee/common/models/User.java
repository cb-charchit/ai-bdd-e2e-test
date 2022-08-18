package com.chargebee.common.models;

public class User {

        public final String firstName;
        public final String lastName;
        public final String email;
        public final String password;

        public User(String firstName, String lastName, String email, String password) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.password = password;
        }
}
