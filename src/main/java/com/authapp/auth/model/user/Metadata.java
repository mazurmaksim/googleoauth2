package com.authapp.auth.model.user;

public class Metadata {
    private boolean primary;
    private boolean verified;
    private Source source;
    private boolean sourcePrimary;

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public boolean isSourcePrimary() {
        return sourcePrimary;
    }

    public void setSourcePrimary(boolean sourcePrimary) {
        this.sourcePrimary = sourcePrimary;
    }
}
