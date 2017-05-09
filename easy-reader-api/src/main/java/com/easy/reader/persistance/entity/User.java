package com.easy.reader.persistance.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Сущность "пользователь".
 * @author dchernyshov
 */
@Entity
@Table(name = "USER")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"userWords", "bookWords"})
@ToString(exclude = {"userWords", "bookWords"})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class User extends BaseEntity {
    private String userName;

    @OneToMany(mappedBy = "userFk")
    private List<UserWord> userWords;
    
    @OneToMany(mappedBy = "userFk")
    private List<BookWord> bookWords;
}
