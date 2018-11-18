package com.bykazarosian.keycloak.providers.forcelogin;

import com.bykazarosian.keycloak.authentication.forcelogin.ForceLoginActionToken;
import org.keycloak.models.KeycloakSession;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

public class ForceLoginResource {
    private final KeycloakSession session;

    public ForceLoginResource(KeycloakSession session) {
        this.session = session;
    }

    @GET
    @Produces("text/plain; charset=utf-8")
    public String get(
            @QueryParam("userId") String userId,
            @QueryParam("clientId") String clientId,
            @QueryParam("expiration") int absoluteExpirationInSecs) {
        ForceLoginActionToken actionToken = new ForceLoginActionToken(userId, absoluteExpirationInSecs, clientId);
        return actionToken.serialize(session, session.getContext().getRealm(), session.getContext().getUri());
    }
}
