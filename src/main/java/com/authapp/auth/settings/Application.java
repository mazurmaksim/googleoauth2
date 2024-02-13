package com.authapp.auth.settings;

import com.authapp.auth.entity.Settings;
import com.google.gson.Gson;

public class Application {
    private static final String AUTH_URL = "https://accounts.google.com/o/oauth2/v2/auth?";
    private static final String TOKEN_URL = "https://oauth2.googleapis.com/token?";

    public static String buildAuthUrl(Settings settings) {
        if (validateSettings(settings)) {
            return AUTH_URL +
                    "client_id=" +
                    settings.getClientId() +
                    "&" +
                    "scope=" +
                    settings.getScope() +
                    "&" +
                    "redirect_uri=" +
                    settings.getRedirectUri() +
                    "&" +
                    "response_type=" +
                    settings.getResourceType() +
                    "&" +
                    "access_type=" +
                    settings.getAccessType() +
                    "&" +
                    "include_granted_scopes=" +
                    settings.isIncludeGrantedScopes();
        } else {
            return null;
        }
    }

    private static boolean validateSettings(Settings settings) {
        return settings.getAccessType() != null &&
                settings.getResourceType() != null &&
                settings.getClientId() != null &&
                settings.getRedirectUri() != null &&
                settings.getScope() != null;
    }

    public static String buildTokenUrl(Settings settings, String code) {
        if (validateSettings(settings)) {
            return TOKEN_URL +
                    "code=" +
                    code +
                    "&" +
                    "client_id=" +
                    settings.getClientId() +
                    "&" +
                    "client_secret="+
                    settings.getClientSecret() +
                    "&" +
                    "redirect_uri=" +
                    settings.getRedirectUri() +
                    "&" +
                    "grant_type=" +
                    "authorization_code";
        } else {
            return null;
        }
    }
}
