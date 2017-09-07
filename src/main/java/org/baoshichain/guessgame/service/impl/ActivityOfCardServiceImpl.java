package org.baoshichain.guessgame.service.impl;

import org.baoshichain.guessgame.dao.ActivityOfCardDao;
import org.baoshichain.guessgame.entity.ActivityOfCard;
import org.baoshichain.guessgame.service.ActivityOfCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityOfCardServiceImpl implements ActivityOfCardService {

    @Autowired
    ActivityOfCardDao activityOfCardDao;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(ActivityOfCard record) {
        return 0;
    }

    @Override
    public int insertSelective(ActivityOfCard record) {
        return 0;
    }

    @Override
    public ActivityOfCard selectByPrimaryKey(Integer id) {
        return activityOfCardDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ActivityOfCard record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(ActivityOfCard record) {
        return 0;
    }

    @Override
    public List<ActivityOfCard> selectActivityOfCardList(int id) {
        return activityOfCardDao.selectActivityOfCardList(id);
    }

    @Override
    public int updateActivityOfcard(int cardid,int activityid) {
        return  activityOfCardDao.updateActivityOfcard(cardid,activityid);
    }
}
