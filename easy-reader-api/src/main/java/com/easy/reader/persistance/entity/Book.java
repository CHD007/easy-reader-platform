package com.easy.reader.persistance.entity;

import com.easy.reader.persistance.dto.BookDto;
import com.easy.reader.persistance.dto.BookWordDto;
import com.easy.reader.persistance.dto.DataTransferable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * Сущность "Книга". Книга состоит из слов "BookWord"
 * @author dchernyshov
 */
@Entity
@Table(name = "BOOK")
@Data
@EqualsAndHashCode(exclude = "bookWords", callSuper = true)
@ToString(callSuper = true, exclude = "bookWords")
@NoArgsConstructor
public class Book extends BaseEntity implements DataTransferable<Book, BookDto>{

    private String bookName;


    @OneToMany(mappedBy = "bookFk", cascade = CascadeType.ALL)
    private List<BookWord> bookWords;

    @Override
    public BookDto toWrapper(Book entity) {
        BookDto bookDto = toShortWrapper(entity);
        ArrayList<BookWordDto> bookWordDtos = new ArrayList<>();
        for (BookWord bookWord: entity.getBookWords()) {
            bookWordDtos.add(bookWord.toWrapper(bookWord));
        }
        bookDto.setBookWords(bookWordDtos);
        return bookDto;
    }

    public BookDto toShortWrapper(Book entity){
        BookDto bookDto = new BookDto();
        bookDto.setId(entity.getId());
        bookDto.setBookName(entity.getBookName());
        bookDto.setWords(entity.getBookWords().size());
        return bookDto;
    }

}
