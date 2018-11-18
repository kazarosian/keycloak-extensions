package com.bykazarosian.keycloak;

import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resource.RealmResourceProvider;

public class RestSignupProvider implements RealmResourceProvider {
    public static final String PROVIDER_ID = "rest-signup";

    private final RestSignup resource;

    public RestSignupProvider(KeycloakSession keycloakSession) {
        resource = new RestSignup(keycloakSession);
    }

    @Override
    public Object getResource() {
        return resource;
    }

    @Override
    public void close() {

    }
}
