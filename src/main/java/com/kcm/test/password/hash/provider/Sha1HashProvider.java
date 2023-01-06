package com.kcm.test.password.hash.provider;

import java.math.BigInteger;
import java.security.MessageDigest;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.credential.hash.PasswordHashProvider;
import org.keycloak.models.PasswordPolicy;
import org.keycloak.models.credential.PasswordCredentialModel;

@Slf4j
public class Sha1HashProvider implements PasswordHashProvider {

  public static final String ALGORITHM = "SHA-1";
  private final String providerId;

  public Sha1HashProvider(final String providerId) {
    this.providerId = providerId;
  }

  @Override
  public boolean policyCheck(PasswordPolicy passwordPolicy, PasswordCredentialModel credential) {
    return this.providerId.equals(credential.getPasswordCredentialData().getAlgorithm());
  }

  @Override
  public PasswordCredentialModel encodedCredential(String rawPassword, int iterations) {
    String encodedPassword = this.encode(rawPassword, iterations);
    return PasswordCredentialModel.createFromValues(
        this.providerId, new byte[0], iterations, encodedPassword);
  }

  @Override
  public boolean verify(String rawPassword, PasswordCredentialModel credential) {
    //log.info("Enter verify {} method", ALGORITHM);
    final String salt =
        new String(
            credential.getPasswordSecretData().getSalt(), java.nio.charset.StandardCharsets.UTF_8);
    //log.info("salt: {}", salt);
    final String encodedPassword =
        this.encode(salt + rawPassword, credential.getPasswordCredentialData().getHashIterations());
    final String hash = credential.getPasswordSecretData().getValue();
    return encodedPassword.equals(hash);
  }

  @Override
  public String encode(String rawPassword, int iterations) {
    try {
      final var md = MessageDigest.getInstance(ALGORITHM);
      md.update(rawPassword.getBytes());
      // convert the digest byte[] to BigInteger
      var aux = new BigInteger(1, md.digest());
      // convert BigInteger to 40-char lowercase string using leading 0s
      return String.format("%040x", aux);
    } catch (Exception e) {
      // fail silently
    }
    return null;
  }

  @Override
  public void close() {
    // empty
  }
}
