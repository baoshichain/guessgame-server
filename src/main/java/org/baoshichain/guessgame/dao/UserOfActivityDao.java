package org.baoshichain.guessgame.dao;

import org.baoshichain.guessgame.entity.UserOfActivity;

<<<<<<< HEAD
import java.util.List;

=======
>>>>>>> d5be35ebbd69282512d4f61ab4316fd0e0d36fd3
public interface UserOfActivityDao {
    int deleteByPrimaryKey(Integer id);

    int insert(UserOfActivity record);

    int insertSelective(UserOfActivity record);

    UserOfActivity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserOfActivity record);

    int updateByPrimaryKey(UserOfActivity record);
<<<<<<< HEAD

    int insertUser(UserOfActivity userOfActivity);

    List<UserOfActivity> getJoinNum(int activityid);
	
	 List<UserOfActivity> selectByActivityId(Integer activityId);
=======
>>>>>>> d5be35ebbd69282512d4f61ab4316fd0e0d36fd3
}