package com.bykazarosian.keycloak.authentication.forcelogin;

import org.keycloak.TokenVerifier;
import org.keycloak.authentication.actiontoken.AbstractActionTokenHander;
import org.keycloak.authentication.actiontoken.ActionTokenContext;
import org.keycloak.common.ClientConnection;
import org.keycloak.events.Errors;
import org.keycloak.events.EventType;
import org.keycloak.models.*;
import org.keycloak.services.managers.AuthenticationManager;
import org.keycloak.services.managers.AuthenticationSessionManager;
import org.keycloak.services.messages.Messages;
import org.keycloak.services.resources.LoginActionsServiceChecks;
import org.keycloak.sessions.CommonClientSessionModel;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;


public class ForceLoginActionTokenHandler extends AbstractActionTokenHander<ForceLoginActionToken> {

    public ForceLoginActionTokenHandler() {
        super(
                ForceLoginActionToken.TOKEN_TYPE,
                ForceLoginActionToken.class,
                Messages.IDENTITY_PROVIDER_LOGIN_FAILURE,
                EventType.RESTART_AUTHENTICATION,
                Errors.NOT_ALLOWED
        );

    }

    @Override
    public TokenVerifier.Predicate<? super ForceLoginActionToken>[] getVerifiers(ActionTokenContext<ForceLoginActionToken> tokenContext) {
        return new TokenVerifier.Predicate[]{
                new LoginActionsServiceChecks.IsActionRequired(tokenContext, CommonClientSessionModel.Action.AUTHENTICATE)
        };
    }

    @Override
    public Response handleToken(ForceLoginActionToken token, ActionTokenContext tokenContext) {
        UserModel user = tokenContext.getSession().getProvider(UserProvider.class).getUserById(token.getUserId(), tokenContext.getRealm());

        KeycloakSession session = tokenContext.getSession();
        RealmModel realm = tokenContext.getRealm();
        ClientConnection clientConnection = tokenContext.getClientConnection();

        UserSessionModel currentUserSession = new AuthenticationSessionManager(tokenContext.getSession()).getUserSession(tokenContext.getAuthenticationSession());
        if (currentUserSession == null) {
            UserSessionModel userSession = session.sessions().createUserSession(realm, user, user.getUsername(), clientConnection.getRemoteAddr(), "impersonate", false, null, null);
            AuthenticationManager.createLoginCookie(session, realm, userSession.getUser(), userSession, session.getContext().getUri(), clientConnection);
            AuthenticationManager.createRememberMeCookie(realm, user.getUsername(), session.getContext().getUri(), clientConnection); // my
        }

        String redirect = tokenContext.getRequest().getUri().getQueryParameters(true).getFirst("redirect");
        try {
            return Response.status(302).location(new URI((redirect != null) ? redirect: "/")).build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean canUseTokenRepeatedly(ForceLoginActionToken token, ActionTokenContext tokenContext) {
        return true;
    }
}
