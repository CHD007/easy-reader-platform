package com.easy.reader.persistance.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.eclipse.persistence.oxm.annotations.XmlInverseReference;

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
@EqualsAndHashCode(callSuper = true, exclude = "context")
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BookWord extends BaseEntity {
    @ElementCollection
    private List<String> context = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "wordFk")
    private Word wordFk;

    @ManyToOne
    @JoinColumn(name = "bookFk")
    @XmlInverseReference(mappedBy = "bookWords")
    private Book bookFk;

    @ManyToOne
    @JoinColumn(name = "userFk")
    @XmlInverseReference(mappedBy = "bookWords")
    private User userFk;
}
