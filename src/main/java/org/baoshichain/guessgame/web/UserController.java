package org.baoshichain.guessgame.web;

import net.sf.json.JSONObject;
import org.baoshichain.guessgame.bean.GameMaker;
import org.baoshichain.guessgame.bean.RoomControll;
import org.baoshichain.guessgame.bean.Userinfo;
import org.baoshichain.guessgame.entity.User;
import org.baoshichain.guessgame.entity.UserOfActivity;
import org.baoshichain.guessgame.service.ActivityService;
import org.baoshichain.guessgame.service.UserOfActivityService;
import org.baoshichain.guessgame.service.UserService;
import org.baoshichain.guessgame.util.CommonUtil;
import org.baoshichain.guessgame.util.TimerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by hisen on 17-4-24.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserOfActivityService userOfActivityService;

    //登录
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject login(User user, HttpSession session) {
        user = userService.checkLogin(user.getPhone(), user.getPassword());
        if (user != null) {
            user.setPassword("");
            // model.addAttribute("user",user);
            session.setAttribute("user", user);
        } else {
            session.setAttribute("user", null);
            return CommonUtil.constructHtmlResponse(201, "用户名或密码错误", null);
        }
        return CommonUtil.constructHtmlResponse(200, "成功", user);
    }

    //用户信息
    @RequestMapping(value = "/main", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject showMain(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            //查询发布活动数量
            int num = userService.getCountofActivity(user.getId());
            //查询参与活动数量
            int joinNum = userService.getCountofJoinActivity(user.getId());
            logger.info("joinNum=" + joinNum);
            GameMaker gameMaker = new GameMaker();
            gameMaker.setUserId(user.getId());
            gameMaker.setContact(user.getQq());
            gameMaker.setEthAddress(user.getEthaddress());
            gameMaker.setToken(user.getToken());
            gameMaker.setPublishCount(num);
            gameMaker.setAmount(user.getBond());
            gameMaker.setJoinCount(joinNum);
            gameMaker.setPhone(user.getPhone());
            gameMaker.setFlag(user.getFlag());
            return CommonUtil.constructHtmlResponse(200, "成功", gameMaker);
        }
        return CommonUtil.constructHtmlResponse(201, "查询失败", null);
    }

    @RequestMapping(value = "/info", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject showUserinfo(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            //查询参与活动数量
            int joinNum = userService.getCountofJoinActivity(user.getId());
            Userinfo userinfo = new Userinfo();
            userinfo.setUserid(user.getId());
            userinfo.setToken(user.getToken());
            userinfo.setJoinnum(joinNum); //参与活动数目
            userinfo.setUsername(user.getLoginname());
            List<Userinfo.ActivityofJoin> userinfolist = new ArrayList<>();

            List<Map> list = activityService.getUserOfActivity(user.getId());
            logger.info("list.size=" + list.size());
            for (int i = 0; i < list.size(); i++) {
                Userinfo.ActivityofJoin info = new Userinfo.ActivityofJoin();
                Map map = list.get(i);
                Iterator entries = map.entrySet().iterator();
                String startBlock = "";
                String endBlock = "";
                int activityId = 0;
                while (entries.hasNext()) {
                    Map.Entry entry = (Map.Entry) entries.next();
                    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                    if (entry.getKey().equals("activityname")) {
                        info.setRoomname((String) entry.getValue());
                    }
                    if (entry.getKey().equals("startBlock")) {
                        startBlock = (String) entry.getValue();
                    }
                    if (entry.getKey().equals("endBlock")) {
                        endBlock = (String) entry.getValue();
                    }
                    if (entry.getKey().equals("id")) {
                        activityId = (Integer) entry.getValue();
                    }
                }
                if (TimerUtil.compare_date(endBlock, TimerUtil.getCurrentTimes()) != 1) { //活动结束
                    continue;
                } else {
                    String time = TimerUtil.getCompareResult(endBlock, TimerUtil.getCurrentTimes());
                    info.setTime(time);
                }
                //房间id
                info.setActivityid(activityId);
                //参加人数
                logger.info("activityId=" + activityId);
                List<UserOfActivity> userlist = userOfActivityService.getJoinNum(activityId);
                logger.info("userlist.size=" + userlist.size());
                info.setJoinnum(userlist.size());
                //房间价值
                int price = activityService.getList(activityId);
                info.setPrice(price);
                //logger.info("price="+price);
                userinfolist.add(info);
            }
            userinfo.setList(userinfolist);
            return CommonUtil.constructHtmlResponse(200, "成功", userinfo);
        }
        return CommonUtil.constructHtmlResponse(201, "查询失败", null);
    }

    //普通用户注册
    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject regist(User user) {
        String phone=user.getPhone();
        int index=userService.checkPhone(phone);
        logger.info("index="+index);
        if(index>0){
            return CommonUtil.constructHtmlResponse(201, "当前号码已注册", null);
        }else{
            int flag = userService.insertUser(user);
            logger.info("flag="+flag);
            if (flag > 0) return CommonUtil.constructHtmlResponse(200, "注册成功", "ok");
            return CommonUtil.constructHtmlResponse(201, "注册失败", null);
        }
    }

    //庄家注册
    @RequestMapping(value = "/regist/admin", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject registAdmin(User user) {
        String phone=user.getPhone();
        int index=userService.checkPhone(phone);
        logger.info("index="+index);
        if(index>0){
            return CommonUtil.constructHtmlResponse(201, "当前号码已注册", null);
        }else{
            int flag = userService.insertAdmin(user);
            logger.info("flag="+flag);
            if (flag > 0) return CommonUtil.constructHtmlResponse(200, "注册成功", "ok");
            return CommonUtil.constructHtmlResponse(201, "注册失败", null);
        }
    }

    //更改保证金
    @RequestMapping(value = "/admin/update", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject updateBond(User user) {
        int index=userService.updateBond(user);
        logger.info("index="+index);
       if(index>0){
            return CommonUtil.constructHtmlResponse(201, "更改成功", null);
        }else{
            return CommonUtil.constructHtmlResponse(201, "更改失败", null);
        }
    }



}


