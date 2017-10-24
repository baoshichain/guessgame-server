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
    public List<Map> getWinnerList() {
        return winnerDao.getWinnerList();
    }

    @Override
    public List<Map> getWinnerList(int id) {
        return winnerDao.getWinnerListByUserId(id);
    }
}
