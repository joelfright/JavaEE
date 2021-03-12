package com.sparta.joel.javaee.authentication;

import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.HashSet;

public class InMemoryIdentityStore implements IdentityStore {

    @Override
    public CredentialValidationResult validate(Credential credential) {
        UsernamePasswordCredential usernamePasswordCredential = (UsernamePasswordCredential) credential;
        if (usernamePasswordCredential.getCaller().equals("admin") && usernamePasswordCredential.getPasswordAsString().equals("root")) {
            HashSet<String> roles = new HashSet<>();
            roles.add("ADMIN");
            return new CredentialValidationResult("admin", roles);
        } else {
            HashSet<String> roles = new HashSet<>();
            roles.add("USER");
            return new CredentialValidationResult(usernamePasswordCredential.getCaller(), roles);
        }
    }
}
