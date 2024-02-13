package com.authapp.auth.entity;

import jakarta.persistence.*;

@Table(name = "app_settings")
@Entity
public class Settings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appId;
    @Column(name = "client_id")
    private String clientId;
    @Column(name="scope")
    private String scope;
    @Column(name = "redirect_uri")
    private String redirectUri;
    @Column(name="resource_type")
    private String resourceType;
    @Column(name="access_type")
    private String accessType;
    @Column(name="client_secret")
    private String clientSecret;
    @Column(name="include_granted_scopes")
    private boolean includeGrantedScopes;

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getAccessType() {
        return accessType;
    }
    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }


    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public boolean isIncludeGrantedScopes() {
        return includeGrantedScopes;
    }

    public void setIncludeGrantedScopes(boolean includeGrantedScopes) {
        this.includeGrantedScopes = includeGrantedScopes;
    }
}
