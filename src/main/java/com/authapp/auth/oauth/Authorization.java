package com.authapp.auth.oauth;

import com.authapp.auth.entity.AppSettings;
import com.authapp.auth.model.user.Person;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static com.authapp.auth.settings.Application.*;

@Component
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

    public static Token refreshToken(Token token, AppSettings settings) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(REFRESH_TOKEN_URL);
        builder
                .queryParam("client_id", settings.getClientId())
                .queryParam("client_secret", settings.getClientSecret())
                .queryParam("refresh_token", token.getRefresh_token())
                .queryParam("grant_type", "refresh_token");

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

    public static Person getUserPersonalInfo(Token token) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(PERSONAL_INFO_URL);
        builder.queryParam("personFields", "emailAddresses")
                .queryParam("access_token", token.getAccess_token());

        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<Person> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                Person.class
        );
        return response.getBody();
    }

    public static void revokeToken(Token token) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(PERSONAL_INFO_URL);
        builder.queryParam("token", token.getAccess_token());

        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class
        );
        System.out.println(response);
    }
}
