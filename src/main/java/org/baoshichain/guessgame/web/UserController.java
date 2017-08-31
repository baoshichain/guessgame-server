package org.baoshichain.guessgame.web;

import net.sf.json.JSONObject;
import org.baoshichain.guessgame.entity.User;
import org.baoshichain.guessgame.service.UserService;
import org.baoshichain.guessgame.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

/**
 * Created by hisen on 17-4-24.
 */
@Controller
@RequestMapping("/user")
public class UserController {
  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private UserService userService;

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  @ResponseBody
  public JSONObject login(User user, Model model){
    user = userService.checkLogin(user.getPhone(), user.getPassword());
    if(user != null){
      user.setPassword("");
      model.addAttribute("user",user);
    }else{
      return CommonUtil.constructHtmlResponse(201,"用户名或密码错误",null);
    }
    return CommonUtil.constructHtmlResponse(200,"成功",user);
  }
}


