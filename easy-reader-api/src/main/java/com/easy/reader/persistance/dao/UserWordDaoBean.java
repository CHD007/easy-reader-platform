package com.easy.reader.persistance.dao;

import com.easy.reader.persistance.entity.UserWord;

import java.util.List;

/**
 * DAO для пользовательских слов
 * @author dchernyshov
 */
public class UserWordDaoBean extends GenericDaoBean<UserWord, Long> {
    public UserWordDaoBean() {
        super(UserWord.class);
    }
    
    @SuppressWarnings({"unchecked", "NonJREEmulationClassesInClientCode"})
    public List<UserWord> getAllWordForUser(Long userId) {
        return entityManager
                .createQuery("select uw from UserWord uw where uw.userFk = ?1")
                .setParameter(1, userId)
                .getResultList();
    }
}
