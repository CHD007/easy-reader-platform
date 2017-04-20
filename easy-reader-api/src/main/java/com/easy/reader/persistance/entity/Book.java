package com.easy.reader.persistance.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class Book extends BaseEntity {
    private String bookName;

    @OneToMany(mappedBy = "bookFk")
    private List<BookWord> bookWords;
}
