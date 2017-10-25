package org.baoshichain.guessgame.service;

import org.baoshichain.guessgame.entity.Winner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface WinnerService {

    int insertWinner(Winner winner);

    List<Map> getWinnerList();

    List<Map> getWinnerList(int id);

    String getWinnerPhoneByActivityId(int activityId);
}
