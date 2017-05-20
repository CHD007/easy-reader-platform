package com.easy.reader.persistance.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for books.
 * @author dchernyshov
 */
@Data
@EqualsAndHashCode(exclude = "bookWords")
@ToString(exclude = "bookWords")
public class BookDto implements Serializable {
    private long id;
    private String bookName;
    private int totalWords;
    private int learnedWords;
    private List<BookWordDto> bookWords;
}
