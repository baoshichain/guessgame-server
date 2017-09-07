package org.baoshichain.guessgame.service;

import org.baoshichain.guessgame.entity.User;
import org.baoshichain.guessgame.entity.UserOfActivity;

import java.util.List;

public interface UserOfActivityService {

    int insertUser(UserOfActivity record);

    List<UserOfActivity> getJoinNum(int activityid);
}
