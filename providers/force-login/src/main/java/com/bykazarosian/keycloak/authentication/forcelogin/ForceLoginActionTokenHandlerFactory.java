package com.bykazarosian.keycloak.authentication.forcelogin;

import org.keycloak.Config;
import org.keycloak.authentication.actiontoken.*;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;


public class ForceLoginActionTokenHandlerFactory implements ActionTokenHandlerFactory<ForceLoginActionToken> {

    @Override
    public ActionTokenHandler<ForceLoginActionToken> create(KeycloakSession session) {
        return new ForceLoginActionTokenHandler();
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

    @Override
    public String getId() {
        return ForceLoginActionToken.TOKEN_TYPE;
    }
}
