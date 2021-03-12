package com.sparta.joel.javaee.beans;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Named
@RequestScoped
public class Logout {

    @Inject
    FacesContext facesContext;

    public String logout() throws ServletException {
        ExternalContext externalContext = facesContext.getExternalContext();
        ((HttpServletRequest) externalContext.getRequest()).logout();
        return "/index.xhtml?faces-redirect=true";
    }

}
