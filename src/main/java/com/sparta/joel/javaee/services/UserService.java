package com.sparta.joel.javaee.services;

import com.sparta.joel.javaee.entities.UsersEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

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
            if (user.getName().equals("Joel") && user.getPassword().equals("password")) {
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

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("javaee");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        entityManager.persist(user);

        transaction.commit();

        return "datainputted";

    }

}
