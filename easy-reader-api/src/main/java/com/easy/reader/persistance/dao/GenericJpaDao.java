package com.easy.reader.persistance.dao;

import com.easy.reader.persistance.entity.BaseEntity;
import com.easy.reader.persistance.exceptions.DaoStoreException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Базовый Generic DAO-объект. Является родителем для
 * всех DAO-объектов сущностей. Выполняет базовые общие
 * операции работы с БД. Специфичные операции для
 * сущностей реализуются в их DAO-объектах
 * @author dchernyshov
 */
public abstract class GenericJpaDao<T extends BaseEntity, I extends Serializable> implements GenericDao<T, I> {
    private final Class<T> persistentClass;    //сущность с которой работаем

    @PersistenceContext(unitName = "ReaderBackend")
    private EntityManager entityManager; //менеджер транзаций

    public GenericJpaDao(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    /**
     * Метод находит объект сущности в БД по его Id
     *
     * @param id - id сущности
     * @return - Объект сущности
     */
    @Override
    public T findById(I id) {
        return entityManager.find(getPersistentClass(), id);
    }

    /**
     * Метод находит все объекты сущности в БД
     *
     * @return - коллекция объектов сущности
     */
    @SuppressWarnings({"unchecked", "NonJREEmulationClassesInClientCode"})
    @Override
    public List<T> findAll() {
        return entityManager
                .createQuery("select x from " + getPersistentClass().getSimpleName() + " x")
                .getResultList();
    }

    /**
     * Метод сохраняет объект сущности в БД
     *
     * @param entity - сохраняемый объект сущности
     */
    @Override
    public T save(T entity) throws DaoStoreException {
        try {
            entityManager.persist(entity);
        } catch (PersistenceException ex) {
            throw new DaoStoreException(ex);
        }
        return entity;
    }

    @Override
    public Collection<T> saveAll(Collection<T> entities) throws DaoStoreException {
        try {
            for(T entity : entities) {
                entityManager.persist(entity);
            }
        } catch (PersistenceException ex) {
            throw new DaoStoreException(ex);
        }
        return entities;
    }

    /**
     * Метод удаляет сущность из БД. Сначала проверяет является
     * ли класс потомком BaseEntity, если да, удаляет по id
     * базовой сущности. Если нет то сначала мержит, потом удаляет.
     *
     * @param entity - удаляемый объект сущности
     */
    @SuppressWarnings("NonJREEmulationClassesInClientCode")
    @Override
    public void delete(T entity) throws DaoStoreException {
        try {
            if (BaseEntity.class.isAssignableFrom(persistentClass)) {
                entityManager.remove(entityManager.getReference(entity.getClass(), ((BaseEntity) entity).getId()));
            } else {
                T mergedEntity = entityManager.merge(entity);
                entityManager.remove(mergedEntity);
            }
        } catch (PersistenceException ex) {
            throw new DaoStoreException(ex);
        }
    }

    /**
     * Обновляет сущность в БД
     *
     * @param entity - обновляемая сущность
     */
    @Override
    public T update(T entity) throws DaoStoreException {
        try {
            entityManager.merge(entity);
        } catch (PersistenceException ex) {
            throw new DaoStoreException(ex);
        }
        return entity;
    }
}
