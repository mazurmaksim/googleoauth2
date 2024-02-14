package com.authapp.auth.oauth;

import com.authapp.auth.entity.AppSettings;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

import static com.authapp.auth.settings.Application.AUTH_URL;
import static com.authapp.auth.settings.Application.TOKEN_URL;

public class Authorization {

    public static Token getToken(AppSettings settings, String code) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TOKEN_URL);
        builder.queryParam("code", code)
                .queryParam("client_id", settings.getClientId())
                .queryParam("client_secret", settings.getClientSecret())
                .queryParam("redirect_uri", settings.getRedirectUri())
                .queryParam("grant_type", "authorization_code");

        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<Token> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.POST,
                entity,
                Token.class
        );
        return response.getBody();
    }

    public static UriComponentsBuilder getAuthCode(AppSettings settings) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(AUTH_URL);
        return builder .queryParam("client_id", settings.getClientId())
                .queryParam("scope", settings.getScope())
                .queryParam("redirect_uri", settings.getRedirectUri())
                .queryParam("response_type", settings.getResponseType())
                .queryParam("access_type", settings.getAccessType())
                .queryParam("include_granted_scopes", settings.isIncludeGrantedScopes());
    }
}
