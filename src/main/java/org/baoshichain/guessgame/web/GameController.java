package org.baoshichain.guessgame.web;

import net.sf.json.JSONObject;
import org.baoshichain.guessgame.bean.EthActivity;
import org.baoshichain.guessgame.bean.EthRoom;
import org.baoshichain.guessgame.contract.Game;
import org.baoshichain.guessgame.entity.Activity;
import org.baoshichain.guessgame.entity.Card;
import org.baoshichain.guessgame.entity.User;
import org.baoshichain.guessgame.entity.UserOfActivity;
import org.baoshichain.guessgame.service.ActivityService;
import org.baoshichain.guessgame.service.UserService;
import org.baoshichain.guessgame.service.WinnerService;
import org.baoshichain.guessgame.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by hisen on 17-4-24.
 */
@Controller
@RequestMapping("/activity")
public class GameController {
    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserService userService;

    @Autowired
    private WinnerService winnerService;



    @RequestMapping("/kjroom/add")
    @ResponseBody
    private JSONObject addjRoom(
            String activityName,
            String needToken,
            String max,
            String min,
            String time,
            String describe,
            String cardInfo,
            HttpSession session
    ) throws Exception {
        //1.先判断用户是不是庄家，非庄家用户不能添加房间
        User user = (User)session.getAttribute("user");
        if(user.getFlag() != 2){
            return CommonUtil.constructHtmlResponse(301,"非庄家用户操作",null);
        }
        //2.将信息添加到数据库中
        System.out.println("activityName"+activityName);
        System.out.println("time:"+time);
        int result = activityService.addKjRoom(user.getId(),activityName,needToken,max,min,time,describe,cardInfo);

        if(result == 1){
            //成功
            return CommonUtil.constructHtmlResponse(200,"ok",null);
        }else{
            //失败
            return CommonUtil.constructHtmlResponse(301,"添加失败",null);
        }
    }

    @RequestMapping("/kjroom/join")
    @ResponseBody
    private JSONObject joinKjRoom(
            String roomId,
            HttpSession session
    ) throws Exception {

        User user = (User)session.getAttribute("user");
        int userId = user.getId();
        //2.将信息添加到数据库中
        int result = activityService.joinLotteryActivity(user.getId(),Integer.parseInt(roomId));
        if(result == 1){
            //成功
            return CommonUtil.constructHtmlResponse(200,"ok",null);
        }else if(result == 2){
            return CommonUtil.constructHtmlResponse(302,"每个房间仅允许一次开奖",null);
        }else if(result == 3) {
            return CommonUtil.constructHtmlResponse(302,"钱不够",null);
        }else{
                //失败
                return CommonUtil.constructHtmlResponse(301,"失败",null);
        }

    }



    @RequestMapping("/kjroom/list")
    @ResponseBody
    private JSONObject kJRoonList(int page) throws IOException, ParseException {
        List<HashMap> kjrooms = activityService.normalKJRoomList(page);
        return CommonUtil.constructHtmlResponse(200,"ok",kjrooms);
    }



    @RequestMapping("/kjroom/detail")
    @ResponseBody
    private JSONObject kJRoomDetail(String roomId,HttpSession session) throws IOException, ParseException {
        User user = (User)session.getAttribute("user");
        int userId = user.getId();
        HashMap dataRoom = activityService.kJRoomDetail(userId,Integer.parseInt(roomId));
        if((int)dataRoom.get("flag") == -1){
            String winnerPhone = winnerService.getWinnerPhoneByActivityId(Integer.parseInt(roomId));
            dataRoom.put("winnerPhone",winnerPhone);
            return CommonUtil.constructHtmlResponse(201,"开奖已经结束",dataRoom);
        }else if((int)dataRoom.get("flag") == -2){
            return CommonUtil.constructHtmlResponse(202,"未达到开奖要求，无中奖者",dataRoom);
        }
        return CommonUtil.constructHtmlResponse(200,"ok",dataRoom);
    }


    @RequestMapping("/kjroom/joinedList")
    @ResponseBody
    private JSONObject joinedKjRoomList(String roomId,HttpSession session) throws IOException, ParseException {
        User user = (User)session.getAttribute("user");
        int userId = user.getId();
        HashMap data = activityService.allJoinedKjRoom(userId);
        return CommonUtil.constructHtmlResponse(200,"ok",data);
    }







}
