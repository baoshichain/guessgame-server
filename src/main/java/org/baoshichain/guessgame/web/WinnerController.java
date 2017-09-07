package org.baoshichain.guessgame.web;

import net.sf.json.JSONObject;
import org.baoshichain.guessgame.entity.User;
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

import javax.servlet.http.HttpSession;

/**
 * Created by hisen on 17-4-24.
 */
@Controller
@RequestMapping("/winner")
public class WinnerController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WinnerService winnerService;

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject insert(User user, HttpSession session){

            //return CommonUtil.constructHtmlResponse(201,"用户名或密码错误",null);

        return CommonUtil.constructHtmlResponse(200,"成功",user);
    }
}
