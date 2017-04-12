package com.easy.reader.persistance.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Сущность "пользователь".
 * @author dchernyshov
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"userWords", "books"})
public class User extends BaseEntity {
    private String userName;

    @OneToMany(mappedBy = "userFk")
    private List<UserWord> userWords;
    
    @OneToMany(mappedBy = "userFk")
    private List<BookWord> bookWords;
}