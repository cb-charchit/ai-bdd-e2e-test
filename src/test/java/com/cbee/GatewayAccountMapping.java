package com.cbee;

public class GatewayAccountMapping {
    private String gateway;
    private String currency;
    private String account;

    public GatewayAccountMapping(String gateway, String currency, String account) {
        this.gateway = gateway;
        this.currency = currency;
        this.account = account;
    }
}
