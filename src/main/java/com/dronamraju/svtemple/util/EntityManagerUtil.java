package com.dronamraju.svtemple.util;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

/**
 * Created by mdronamr on 2/28/17.
 */
public class EntityManagerUtil {

    @PersistenceUnit(unitName="parakamani-jpa")
    private static EntityManagerFactory parakamaniEntityManagerFactory;

    @PersistenceUnit(unitName="parakamani-jpa")
    private static EntityManager parakamaniEntityManager;

    private EntityManagerUtil() {
    }

    public static EntityManager getParakamaniEntityManager() {
        if(parakamaniEntityManager==null){
            parakamaniEntityManagerFactory = Persistence.createEntityManagerFactory("parakamani-jpa");
            parakamaniEntityManager = parakamaniEntityManagerFactory.createEntityManager();
        }
        return parakamaniEntityManager;
    }
}