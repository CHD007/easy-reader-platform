package com.easy.reader.persistance.dto;

import com.easy.reader.persistance.entity.Status;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for Words.
 * @author dchernyshov
 */
@Data
public class WordDto implements Serializable {
    private String wordName;
    private String transcription;
    private String translation;
    private Status status;
    private List<String> context;
}
