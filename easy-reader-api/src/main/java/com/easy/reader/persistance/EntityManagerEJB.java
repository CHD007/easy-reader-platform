package com.easy.reader.persistance;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

/**
 * @author dchernyshov
 */
@Stateless
public class EntityManagerEJB {
    @PersistenceUnit
    private EntityManager entityManager;

    private EntityManagerEJB() {}

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
