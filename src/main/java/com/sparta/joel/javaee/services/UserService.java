package com.sparta.joel.javaee.services;

import com.sparta.joel.javaee.entities.UsersEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.*;
import java.util.List;

@Named
@RequestScoped
public class UserService {

    @Inject
    UsersEntity user;
    String loginType;

    public UsersEntity getUser() {
        return user;
    }

    public void setUser(UsersEntity user) {
        this.user = user;
    }

    public String welcome() {
        if (loginType.equals("user")) {
            if (userExists()) {
                return "welcome";
            }
        } else if (loginType.equals("admin")) {
            if (user.getName().equals("admin") && user.getPassword().equals("root")) {
                return "admin";
            }
        }
        return "loginError";
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String persistData() {
        EntityManager em =getEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        return "datainputted";
    }

    public boolean userExists(){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT u FROM UsersEntity u WHERE u.name = ?1 AND u.password = ?2");
        query.setParameter(1,user.getName());
        query.setParameter(2,user.getPassword());
        try{
            query.getSingleResult();
            return true;
        }catch(javax.persistence.NoResultException e){
            return false;
        }
    }

    public EntityManager getEntityManager(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("javaee");
        return entityManagerFactory.createEntityManager();
    }

}
