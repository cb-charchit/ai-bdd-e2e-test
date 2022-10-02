package com.cbee;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Token {
    @JsonProperty("access_token")
    String accessToken;
    @JsonProperty("refresh_token")
    String refreshToken;
    @JsonProperty("expires_in")
    String expiresIn;
    @JsonProperty("x_refresh_token_expires_in")
    String xRefreshTokenExpiresIn;
    @JsonProperty("authType")
    String authType;
    @JsonProperty("token_type")
    String tokenType;

    public Token() {
    }

    public Token(String accessToken, String refresh_token, String expiresIn, String xRefreshTokenExpiresIn, String authType, String tokenType) {
        this.accessToken = accessToken;
        this.refreshToken = refresh_token;
        this.expiresIn = expiresIn;
        this.xRefreshTokenExpiresIn = xRefreshTokenExpiresIn;
        this.authType = authType;
        this.tokenType = tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
