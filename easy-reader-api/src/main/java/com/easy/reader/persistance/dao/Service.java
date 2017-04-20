package com.easy.reader.persistance.dao;

import com.easy.reader.persistance.dto.WordDto;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for getting word's wrapper.
 * @author dchernyshov
 */
@Stateless
public class Service {
    @Inject
    UserWordDao dao;
    
    public List<WordDto> getAllWords() {
        return dao.findAll().stream().map(en -> en.toWrapper(en)).collect(Collectors.toList());
    }
}
