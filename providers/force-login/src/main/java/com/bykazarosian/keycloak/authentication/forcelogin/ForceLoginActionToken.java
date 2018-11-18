package com.bykazarosian.keycloak.authentication.forcelogin;

import org.keycloak.authentication.actiontoken.DefaultActionToken;

public class ForceLoginActionToken extends DefaultActionToken {
    public static final String TOKEN_TYPE = "force-login";

    public ForceLoginActionToken(String userId, int absoluteExpirationInSecs, String clientId) {
        super(userId, TOKEN_TYPE, absoluteExpirationInSecs, null, null);
        this.issuedFor = clientId;
    }

    private ForceLoginActionToken() {
    }
}
