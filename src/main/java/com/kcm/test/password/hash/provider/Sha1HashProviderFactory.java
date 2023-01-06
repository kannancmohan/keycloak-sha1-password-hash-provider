package com.kcm.test.password.hash.provider;

import org.keycloak.Config.Scope;
import org.keycloak.credential.hash.PasswordHashProvider;
import org.keycloak.credential.hash.PasswordHashProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class Sha1HashProviderFactory implements PasswordHashProviderFactory {

  public static final String ID = "sha1-salted";

  @Override
  public PasswordHashProvider create(KeycloakSession keycloakSession) {
    return new Sha1HashProvider(getId());
  }

  @Override
  public void init(Scope scope) {

  }

  @Override
  public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

  }

  @Override
  public void close() {

  }

  @Override
  public String getId() {
    return ID;
  }
}
