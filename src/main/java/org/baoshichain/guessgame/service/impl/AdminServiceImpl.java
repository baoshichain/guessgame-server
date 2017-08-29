package org.baoshichain.guessgame.service.impl;

import org.baoshichain.guessgame.dao.AdminDao;
import org.baoshichain.guessgame.dao.UserDao;
import org.baoshichain.guessgame.entity.Admin;
import org.baoshichain.guessgame.entity.User;
import org.baoshichain.guessgame.service.AdminService;
import org.baoshichain.guessgame.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * Created by hisen on 17-4-24.
 */
@Service
public class AdminServiceImpl implements AdminService, Serializable {
  @Autowired
  private AdminDao adminDao;


  @Override
  public int insert(Admin record) {
    return 0;
  }

  @Override
  public int insertSelective(Admin record) {
    return 0;
  }
}
