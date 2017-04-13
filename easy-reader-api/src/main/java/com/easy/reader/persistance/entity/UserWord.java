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
    @JoinColumn(name = "wordFk")
    private Word wordFk;

    @OneToOne
    @JoinColumn(name = "bookWordFk")
    private BookWord bookWordFk;

    @ManyToOne
    @JoinColumn(name = "userFk")
    private User userFk;
}
