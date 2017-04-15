package com.easy.reader.persistance.dto;

import java.io.Serializable;

/**
 * Assembler for DTO.
 * @author dchernyshov
 */
public interface DataTransferable<Entity, Wrapper extends Serializable> {
    /**
     * @param entity entity for which DTO is created
     * @return DTO для Entity
     */
    Wrapper toWrapper(Entity entity);
}
