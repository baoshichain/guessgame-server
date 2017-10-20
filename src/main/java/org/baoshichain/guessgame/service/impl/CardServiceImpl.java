package org.baoshichain.guessgame.service.impl;

import org.baoshichain.guessgame.dao.ActivityOfCardDao;
import org.baoshichain.guessgame.dao.CardDao;
import org.baoshichain.guessgame.entity.ActivityOfCard;
import org.baoshichain.guessgame.entity.Card;
import org.baoshichain.guessgame.service.ActivityOfCardService;
import org.baoshichain.guessgame.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardDao cardDao;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(Card record) {
        return 0;
    }

    @Override
    public int insertSelective(Card record) {
        return 0;
    }

    @Override
    public Card selectByPrimaryKey(Integer id) {
        return cardDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Card record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Card record) {
        return 0;
    }
}
