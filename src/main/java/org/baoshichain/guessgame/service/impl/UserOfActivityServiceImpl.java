package org.baoshichain.guessgame.service.impl;

import org.baoshichain.guessgame.dao.ActivityDao;
import org.baoshichain.guessgame.dao.UserOfActivityDao;
import org.baoshichain.guessgame.entity.UserOfActivity;
import org.baoshichain.guessgame.service.UserOfActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserOfActivityServiceImpl implements UserOfActivityService {

    @Autowired
    private UserOfActivityDao userOfActivityDao;

    @Override
    public int insertUser(UserOfActivity record) {
        return userOfActivityDao.insertUser(record);
    }

    @Override
    public List<UserOfActivity> getJoinNum(int activityid,int userid) {
        return userOfActivityDao.getJoinNum(activityid,userid);
    }

    @Override
    public List<UserOfActivity> getJoinUserNum(int activityid) {
        return userOfActivityDao.getJoinUserNum(activityid);
    }


}
