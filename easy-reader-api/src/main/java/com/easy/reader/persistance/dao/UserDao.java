package com.easy.reader.persistance.dao;

import com.easy.reader.persistance.entity.User;

import javax.ejb.Stateless;

/**
 * DAO для пользователей
 * @author dchernyshov
 */
@Stateless
public class UserDao extends GenericDao<User, Long> {
    public UserDao() {
        super(User.class);
    }
}
