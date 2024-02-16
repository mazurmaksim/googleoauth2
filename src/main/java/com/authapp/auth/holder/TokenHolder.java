package com.authapp.auth.holder;

import com.authapp.auth.oauth.Token;
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
