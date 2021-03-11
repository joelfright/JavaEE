package com.sparta.joel.javaee.datastore;

import com.sparta.joel.javaee.entities.UsersEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

@Named
@RequestScoped
public class PersistenceManager {

    {
        persistData();
    }

    public void persistData(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        UsersEntity user = new UsersEntity();
        user.setId(1);
        user.setName("Joel Fright");
        user.setEmail("jfright@spartaglobal.com");
        entityManager.persist(user);

        UsersEntity user2 = new UsersEntity();
        user.setId(2);
        user.setName("John Smith");
        user.setEmail("jsmith@spartaglobal.com");
        entityManager.persist(user2);

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();

    }

}
