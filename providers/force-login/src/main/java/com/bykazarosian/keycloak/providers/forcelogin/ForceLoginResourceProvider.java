package com.bykazarosian.keycloak.providers.forcelogin;

import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resource.RealmResourceProvider;

public class ForceLoginResourceProvider implements RealmResourceProvider {
    private final KeycloakSession session;

    public ForceLoginResourceProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public Object getResource() {
        return new ForceLoginResource(session);
    }

    @Override
    public void close() {
    }
}
