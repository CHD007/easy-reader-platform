package com.easy.reader.persistance.dao;

import com.easy.reader.persistance.entity.User;

/**
 * DAO для пользователей
 * @author dchernyshov
 */
public class UserDaoBean extends GenericDaoBean<User, Long> {
    public UserDaoBean() {
        super(User.class);
    }
}
