package com.easy.reader.persistance.dao;

import com.easy.reader.persistance.entity.BaseEntity;
import com.easy.reader.persistance.exceptions.DaoStoreException;

import javax.ejb.Stateless;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @param <T> тип самой сущности
 * @param <I> тип id сущности
 * @author dchernyshov
 */
@Stateless
public interface GenericDao<T extends BaseEntity, I extends Serializable> {
    /**
     * Метод находит объект сущности в БД по его Id
     *
     * @param id - id сущности
     * @return - Объект сущности
     */
    T findById(I id);
    
    void create(T entity) throws DaoStoreException;

    /**
     * Метод находит все объекты сущности в БД
     *
     * @return - коллекция объектов сущности
     */
    List<T> findAll();

    /**
     * Метод сохраняет объект сущности в БД
     *
     * @param entity - сохраняемый объект сущности
     */
    T save(T entity) throws DaoStoreException;

    /**
     * Метод удаляет сущность из БД. Сначала проверяет является
     * ли класс потомком BaseEntity, если да, удаляет по id
     * базовой сущности. Если нет то сначала мержит, потом удаляет.
     *
     * @param entity - удаляемый объект сущности
     */
    void delete(T entity) throws DaoStoreException;

    /**
     * Обновляет сущность в БД
     *
     * @param entity - обновляемая сущность
     */
    T update(T entity) throws DaoStoreException;

    Collection<T> saveAll(Collection<T> entities) throws DaoStoreException;
}
