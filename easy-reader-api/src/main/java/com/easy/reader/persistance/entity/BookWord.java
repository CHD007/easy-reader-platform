package com.easy.reader.persistance.entity;

import com.easy.reader.persistance.dto.BookWordDto;
import com.easy.reader.persistance.dto.DataTransferable;
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
 *
 * @author dchernyshov
 */
@Entity
@Table(name = "BOOK_WORD")
@Data
@EqualsAndHashCode(callSuper = true, exclude = "context")
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BookWord extends BaseEntity implements DataTransferable<BookWord, BookWordDto> {
    @Lob
    private String context;

    @OneToOne(fetch = FetchType.EAGER)
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

    @Enumerated(EnumType.STRING)
    private Status status = Status.NEW;

    @Override
    public BookWordDto toWrapper(BookWord entity) {
        BookWordDto bookWordDto = new BookWordDto();
        bookWordDto.setId(entity.getId());
        bookWordDto.setContext(entity.getContext());
        bookWordDto.setTranscription(entity.getWordFk().getTranscription());
        bookWordDto.setTranslation(entity.getWordFk().getTranslation());
        bookWordDto.setWordName(entity.getWordFk().getWordName());
        bookWordDto.setBookName(entity.getBookFk().getBookName());
        bookWordDto.setStatus(entity.getStatus());
        return bookWordDto;
    }
}
