package com.easy.reader.persistance.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Сущность "Слово". Содержит само слово, перевод, транскрипцию
 * @author dchernyshov
 */
@Entity
@Table(name = "WORD")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Word extends BaseEntity {
    private String wordName;
    private String transcription;
    private String translation;
}
