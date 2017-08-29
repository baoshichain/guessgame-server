package org.baoshichain.guessgame.service;

import org.baoshichain.guessgame.entity.Admin;

public interface AdminService {
    int insert(Admin record);

    int insertSelective(Admin record);
}