package com.easy.reader.persistance.entity;

import com.easy.reader.persistance.dto.DataTransferable;
import com.easy.reader.persistance.dto.WordDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * Сущность пользовательского слова.
 * @author dchernyshov
 */
@Entity(name = "UserWord")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserWord extends BaseEntity implements DataTransferable<UserWord, WordDto> {
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne
    @JoinColumn(name = "wordFk")
    private Word wordFk;

    @OneToOne
    @JoinColumn(name = "bookWordFk")
    private BookWord bookWordFk;

    @ManyToOne
    @JoinColumn(name = "userFk")
    private User userFk;
    
    @Override
    public WordDto toWrapper(UserWord userWord) {
        WordDto wordDto = new WordDto();
        wordDto.setWordName(userWord.getWordFk().getWordName());
        wordDto.setTranslation(userWord.getWordFk().getTranslation());
        wordDto.setTranscription(userWord.getWordFk().getTranscription());
        wordDto.setStatus(userWord.getStatus());
        wordDto.setContext(userWord.getBookWordFk().getContext());
        return wordDto;
    }
}
