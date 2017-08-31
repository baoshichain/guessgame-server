package org.baoshichain.guessgame.service;

import org.baoshichain.guessgame.entity.User;

public interface UserService {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User checkLogin(String phone,String password);
}