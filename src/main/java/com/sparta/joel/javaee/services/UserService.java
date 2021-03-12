package com.sparta.joel.javaee.services;

import com.sparta.joel.javaee.entities.UsersEntity;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.*;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;

@Named
@RequestScoped
public class UserService {

    @Inject
    UsersEntity user;
    String loginType;

    @Inject
    SecurityContext securityContext;

    @Inject
    ExternalContext externalContext;

    @Inject
    FacesContext facesContext;

    public UsersEntity getUser() {
        return user;
    }

    public void setUser(UsersEntity user) {
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
                        externalContext.redirect(externalContext.getRequestContextPath() + "/admin.xhtml");
                    } else {
                        externalContext.redirect(externalContext.getRequestContextPath() + "/welcome.xhtml");
                    }
                } else {
                    externalContext.redirect(externalContext.getRequestContextPath() + "/loginError.xhtml");
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

    public String logout() throws ServletException {
        ExternalContext externalContext = facesContext.getExternalContext();
        ((HttpServletRequest) externalContext.getRequest()).logout();
        return "/index.xhtml?faces-redirect=true";
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public EntityManager getEntityManager() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("javaee");
        return entityManagerFactory.createEntityManager();
    }

    public String persistData() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        return "datainputted";
    }

    public boolean userExists() {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT u FROM UsersEntity u WHERE u.name = ?1 AND u.password = ?2");
        query.setParameter(1, user.getName());
        query.setParameter(2, user.getPassword());
        try {
            query.getSingleResult();
            return true;
        } catch (javax.persistence.NoResultException e) {
            return false;
        }
    }

    public String login(){
        return "loginPage";
    }

}
