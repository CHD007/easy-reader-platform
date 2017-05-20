package com.easy.reader.persistance.dto;

import com.easy.reader.persistance.entity.Status;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for BookWords.
 * @author dchernyshov
 */
@Data
public class BookWordDto implements Serializable {
    private long id;
    private String wordName;
    private String transcription;
    private String translation;
    private Status status;
    private String bookName;
    private List<String> context;
}
