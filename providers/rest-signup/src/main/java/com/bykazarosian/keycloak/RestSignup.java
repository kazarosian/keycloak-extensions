package com.bykazarosian.keycloak;

import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.utils.ModelToRepresentation;
import org.keycloak.models.utils.RepresentationToModel;
import org.keycloak.representations.idm.UserRepresentation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

public class RestSignup  {
    private final KeycloakSession session;

    public RestSignup(KeycloakSession session) {
        this.session = session;
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public UserRepresentation doPost(@PathParam("realm") String realmName, UserRepresentation userRepresentation) {
        RealmModel realm = session.realms().getRealmByName(realmName);
        if(realm.isRegistrationEmailAsUsername() && userRepresentation.getUsername() == null) {
            userRepresentation.setUsername(userRepresentation.getEmail());
        }
        UserModel user = RepresentationToModel.createUser(session, realm, userRepresentation);
        user.setEnabled(true);
        return ModelToRepresentation.toBriefRepresentation(user);
    }
}
