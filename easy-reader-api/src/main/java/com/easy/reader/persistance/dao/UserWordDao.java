package com.easy.reader.persistance.dao;

import com.easy.reader.persistance.entity.UserWord;

import javax.ejb.Stateless;
import java.util.List;

/**
 * DAO для пользовательских слов
 * @author dchernyshov
 */
@Stateless
public class UserWordDao extends GenericDao<UserWord, Long> {
    public UserWordDao() {
        super(UserWord.class);
    }
    
    public List<UserWord> findAllWordByUserId(Long userId) {
        return entityManager
                .createQuery("select uw from UserWord uw where uw.userFk = ?1", UserWord.class)
                .setParameter(1, userId)
                .getResultList();
    }
}
