package com.sparta.joel.javaee.beans;

import com.sparta.joel.javaee.entities.UserEntity;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Named
@RequestScoped
public class Login {

    @Inject
    UserEntity user;

    @Inject
    SecurityContext securityContext;

    @Inject
    ExternalContext externalContext;

    @Inject
    FacesContext facesContext;

    public String login() {
        return "view/loginPage.xhtml";
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void welcome() throws IOException {
        switch (continueAuthentication()) {
            case SEND_CONTINUE:
                facesContext.responseComplete();
                break;
            case SEND_FAILURE:
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login unsuccessful", null));
                break;
            case SUCCESS:
                if (userExists()) {
                    if (securityContext.isCallerInRole("ADMIN")) {
                        externalContext.redirect(externalContext.getRequestContextPath() + "/view/admin.xhtml");
                    } else {
                        externalContext.redirect(externalContext.getRequestContextPath() + "/view/welcome.xhtml");
                    }
                } else {
                    externalContext.redirect(externalContext.getRequestContextPath() + "/view/loginError.xhtml");
                }
        }
    }

    private AuthenticationStatus continueAuthentication() {
        return securityContext.authenticate(
                (HttpServletRequest) externalContext.getRequest(),
                (HttpServletResponse) externalContext.getResponse(),
                AuthenticationParameters.withParams().credential(new UsernamePasswordCredential(user.getName(), user.getPassword()))
        );
    }

    public boolean userExists() {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT u FROM UserEntity u WHERE u.name = ?1 AND u.password = ?2");
        query.setParameter(1, user.getName());
        query.setParameter(2, user.getPassword());
        try {
            query.getSingleResult();
            return true;
        } catch (javax.persistence.NoResultException e) {
            return false;
        }
    }

    public EntityManager getEntityManager() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("javaee");
        return entityManagerFactory.createEntityManager();
    }
}
