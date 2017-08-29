package org.baoshichain.guessgame.dao;

import org.baoshichain.guessgame.entity.Admin;

public interface AdminDao {
    int insert(Admin record);

    int insertSelective(Admin record);
}