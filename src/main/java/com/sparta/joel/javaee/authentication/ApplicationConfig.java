package com.sparta.joel.javaee.authentication;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;

@CustomFormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage = "/view/index.xhtml",
                errorPage = "/view/loginError.xhtml",
                useForwardToLogin = false
        )
)

@FacesConfig
@ApplicationScoped
public class ApplicationConfig {
}
