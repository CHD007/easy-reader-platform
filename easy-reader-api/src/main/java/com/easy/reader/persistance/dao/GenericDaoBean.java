package com.easy.reader.persistance.dao;

import com.easy.reader.persistance.entity.BaseEntity;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
@Singleton
@Startup
public class GenericDaoBean<T extends BaseEntity, I extends Serializable> {
    private Class<T> persistentClass;    //сущность с которой работаем

    @PersistenceContext(unitName = "ReaderBackend")
    private EntityManager entityManager; //менеджер транзаций
    
    public GenericDaoBean() {
    }
    
    public GenericDaoBean(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
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
    public T findById(I id) {
        return entityManager.find(getPersistentClass(), id);
    }

    /**
     * Метод находит все объекты сущности в БД
     *
     * @return - коллекция объектов сущности
     */
    @SuppressWarnings({"unchecked", "NonJREEmulationClassesInClientCode"})
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
    public void save(T entity) {
        entityManager.persist(entity);
    }
    
    public void create(T entity) {
        entityManager.merge(entity);
    }
    
    public void saveAll(Collection<T> entities) {
        for(T entity : entities) {
            entityManager.persist(entity);
        }
    }

    /**
     * Метод удаляет сущность из БД. Сначала проверяет является
     * ли класс потомком BaseEntity, если да, удаляет по id
     * базовой сущности. Если нет то сначала мержит, потом удаляет.
     *
     * @param entity - удаляемый объект сущности
     */
    @SuppressWarnings("NonJREEmulationClassesInClientCode")
    public void delete(T entity) {
        if (BaseEntity.class.isAssignableFrom(persistentClass)) {
            entityManager.remove(entityManager.getReference(entity.getClass(), ((BaseEntity) entity).getId()));
        } else {
            T mergedEntity = entityManager.merge(entity);
            entityManager.remove(mergedEntity);
        }
    }

    /**
     * Обновляет сущность в БД
     *
     * @param entity - обновляемая сущность
     */
    public T update(T entity) {
        return entityManager.merge(entity);
    }
}
