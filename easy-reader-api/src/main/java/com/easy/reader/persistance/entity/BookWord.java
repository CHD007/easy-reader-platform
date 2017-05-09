package com.easy.reader.persistance.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Слово из книги. Имеет контекст из книги.
 * @author dchernyshov
 */
@Entity
@Table(name = "BOOK_WORD")
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BookWord extends BaseEntity {
    @ElementCollection
    private List<String> context = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "wordFk")
    private Word wordFk;

    @ManyToOne
    @JoinColumn(name = "bookFk")
    private Book bookFk;

    @ManyToOne
    @JoinColumn(name = "userFk")
    private User userFk;
}
