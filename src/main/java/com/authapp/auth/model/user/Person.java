package com.authapp.auth.model.user;

import java.util.List;

public class Person {
    private String resourceName;
    private String etag;
    private List<EmailAddress> emailAddresses;

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public List<EmailAddress> getEmailAddresses() {
        return emailAddresses;
    }

    public void setEmailAddresses(List<EmailAddress> emailAddresses) {
        this.emailAddresses = emailAddresses;
    }

    @Override
    public String toString() {
        return "Person{" +
                "resourceName='" + resourceName + '\'' +
                ", etag='" + etag + '\'' +
                ", emailAddresses=" + emailAddresses +
                '}';
    }
}
