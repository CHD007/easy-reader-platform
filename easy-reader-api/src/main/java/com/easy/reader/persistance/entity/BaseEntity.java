package com.easy.reader.persistance.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;


@Entity
@MappedSuperclass
@NoArgsConstructor
@Data
public class BaseEntity implements Serializable {
    private long id;
}