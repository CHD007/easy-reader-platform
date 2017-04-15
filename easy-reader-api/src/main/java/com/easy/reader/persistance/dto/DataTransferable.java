package com.easy.reader.persistance.dto;

import com.easy.reader.persistance.entity.BaseEntity;

import java.io.Serializable;

/**
 * Assembler for DTO.
 * @author dchernyshov
 */
public interface DataTransferable<Entity extends BaseEntity, Wrapper extends Serializable> {
    /**
     * @param entity entity for which DTO is created
     * @return DTO для Entity
     */
    Wrapper toWrapper(Entity entity);
}
