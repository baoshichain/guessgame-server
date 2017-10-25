package org.baoshichain.guessgame.service.impl;

import org.baoshichain.guessgame.dao.UserOfActivityDao;
import org.baoshichain.guessgame.dao.WinnerDao;
import org.baoshichain.guessgame.entity.Winner;
import org.baoshichain.guessgame.service.WinnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WinnerServiceImpl implements WinnerService {

    @Autowired
    private WinnerDao winnerDao;

    @Override
    public int insertWinner(Winner winner) {
        return winnerDao.insertWinner(winner);
    }

    @Override
    public List<Map> getWinnerList(int page) {
        return winnerDao.getWinnerList((page-1)*5);
    }

    @Override
    public List<Map> getWinnerList(int id,int page) {
        return winnerDao.getWinnerListByUserId(id,(page-1)*3);
    }

    @Override
    public String getWinnerPhoneByActivityId(int activityId) {
        return winnerDao.selectWinnerPhoneByActivityId(activityId);
    }

}
