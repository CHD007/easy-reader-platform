package com.easy.reader.persistance.dao;

import com.easy.reader.persistance.entity.BaseEntity;
import com.easy.reader.persistance.exceptions.DaoStoreException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnit;
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
public abstract class GenericJpaDao<T, I extends Serializable> implements GenericDao<T, I> {
    private static int MAX_BATCH = 50;
    private final Class<T> persistentClass;    //сущность с которой работаем

    @PersistenceUnit
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

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
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
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
        } catch (PersistenceException ex) {
            entityManager.getTransaction().rollback();
            throw new DaoStoreException(ex);
        }
        return entity;
    }

    @Override
    public Collection<T> saveAll(Collection<T> entities) throws DaoStoreException {
        try {
            int i = 0;
            entityManager.getTransaction().begin();
            for(T entity : entities) {
                entityManager.persist(entity);
                if( i % MAX_BATCH == 0 ) {
                    flush();
                    clean();
                }
            }
            entityManager.getTransaction().commit();
        } catch (PersistenceException ex) {
            entityManager.getTransaction().rollback();
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
            entityManager.getTransaction().begin();
            if (BaseEntity.class.isAssignableFrom(persistentClass)) {
                entityManager.remove(entityManager.getReference(entity.getClass(), ((BaseEntity) entity).getId()));
            } else {
                T mergedEntity = entityManager.merge(entity);
                entityManager.remove(mergedEntity);
            }
            entityManager.getTransaction().commit();
        } catch (PersistenceException ex) {
            entityManager.getTransaction().rollback();
            throw new DaoStoreException(ex);
        }
    }

    /**
     * Форсит изменения произведенные в сушностях
     * во время транзакции
     */
    @Override
    public void flush() {
        entityManager.flush();
    }

    /**
     * Очищает контекст сессии
     */
    @Override
    public void clean() {
        entityManager.clear();
    }

    /**
     * Обновляет сущность в БД
     *
     * @param entity - обновляемая сущность
     */
    @Override
    public T update(T entity) throws DaoStoreException {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(entity);
            entityManager.getTransaction().commit();
        } catch (PersistenceException ex) {
            entityManager.getTransaction().rollback();
            throw new DaoStoreException(ex);
        }
        return entity;
    }
}
