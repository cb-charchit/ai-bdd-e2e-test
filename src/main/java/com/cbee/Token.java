package com.cbee;

public class Token {
    String access_token;
    String refresh_token;
    String expires_in;
    String x_refresh_token_expires_in;
    String authType;
    String token_type;

    public Token() {
    }

    public Token(String access_token, String refresh_token, String expires_in, String x_refresh_token_expires_in, String authType, String token_type) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.expires_in = expires_in;
        this.x_refresh_token_expires_in = x_refresh_token_expires_in;
        this.authType = authType;
        this.token_type = token_type;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getX_refresh_token_expires_in() {
        return x_refresh_token_expires_in;
    }

    public void setX_refresh_token_expires_in(String x_refresh_token_expires_in) {
        this.x_refresh_token_expires_in = x_refresh_token_expires_in;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }
}
