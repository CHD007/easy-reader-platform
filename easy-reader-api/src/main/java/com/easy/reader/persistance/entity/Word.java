package com.easy.reader.persistance.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

/**
 * Сущность "Слово". Содержит само слово, перевод, транскрипцию
 * @author dchernyshov
 */
@Entity(name = "Word")
@Data
@EqualsAndHashCode(callSuper = true)
public class Word extends BaseEntity {
    private String wordName;
    private String transcription;
    private String translation;
}
