package com.bykazarosian.keycloak.providers.forcelogin;

import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.services.resource.RealmResourceProvider;
import org.keycloak.services.resource.RealmResourceProviderFactory;

public class ForceLoginResourceProviderFactory implements RealmResourceProviderFactory {
    public static final String PROVIDER_ID = "force-login";

    @Override
    public RealmResourceProvider create(KeycloakSession session) {
        return new ForceLoginResourceProvider(session);
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public void init(Config.Scope config) {
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
    }

    @Override
    public void close() {
    }
}
