package com.easy.reader.persistance;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

/**
 * Entity Manager Factory
 * @author Чернышов Даниил chernyshov.daniil@nicetu.spb.ru
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
