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
 * Сущность "Книга". Книга состоит из слов "BookWord"
 * @author dchernyshov
 */
@Entity
@Table(name = "BOOK")
@Data
@EqualsAndHashCode(exclude = "bookWords", callSuper = true)
@ToString(exclude = "bookWords")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Book extends BaseEntity {
    private String bookName;

    @OneToMany(mappedBy = "bookFk")
    private List<BookWord> bookWords;
}
