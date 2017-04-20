package com.easy.reader.persistance.dto;

import com.easy.reader.persistance.entity.BaseEntity;

import java.io.Serializable;

/**
 * Assembler for DTO.
 * @author dchernyshov
 * @param <T> type of entity to which wrapper is creating for
 * @param <W> type of wrapper
 */
public interface DataTransferable<T extends BaseEntity, W extends Serializable> {
    /**
     * @param entity entity for which DTO is created
     * @return DTO для T
     */
    W toWrapper(T entity);
}
