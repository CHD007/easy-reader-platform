package com.easy.reader.persistance.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Слово из книги. Имеет контекст из книги.
 * @author dchernyshov
 */
@Entity(name = "BookWord")
@Data
@EqualsAndHashCode(callSuper = true)
public class BookWord extends BaseEntity {
    @ElementCollection
    private List<String> context = new ArrayList<>();

    @OneToOne
    private Word wordFk;

    @ManyToOne
    private Book bookFk;

    @ManyToOne
    private User userFk;
}