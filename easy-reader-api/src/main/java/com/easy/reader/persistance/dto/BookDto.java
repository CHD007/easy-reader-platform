package com.easy.reader.persistance.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for books.
 *
 * @author dchernyshov
 */
@Data
@EqualsAndHashCode(exclude = "bookWords")
@ToString(exclude = "bookWords")
public class BookDto implements Serializable {
    private long id;
    private String bookName;
    private long words;
    private long learnedWords;
    private long inProgress;
    private long newWords;
    private List<BookWordDto> bookWords;


    public BookDto calculateNewWords() {
        newWords = words - learnedWords - inProgress;
        return this;
    }
}
