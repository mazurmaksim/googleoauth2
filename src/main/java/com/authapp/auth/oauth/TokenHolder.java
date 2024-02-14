package com.authapp.auth.oauth;

import org.springframework.stereotype.Component;

@Component
public class TokenHolder {
    private Token token;

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
