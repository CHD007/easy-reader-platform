package com.easy.reader.persistance.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * Сущность пользовательского слова.
 * @author dchernyshov
 */
@Entity(name = "UserWord")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserWord extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne
    @JoinColumn(name = "id")
    private Word wordFk;

    @OneToOne
    @JoinColumn(name = "id")
    private BookWord bookWordFk;

    @ManyToOne
    private User userFk;
}
