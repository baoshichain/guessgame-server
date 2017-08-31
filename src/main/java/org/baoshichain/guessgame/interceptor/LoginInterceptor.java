package org.baoshichain.guessgame.interceptor;

import org.baoshichain.guessgame.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by think on 2017-08-31.
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        //首先获取请求的url
        String url = request.getRequestURI();
        //判断url是否是公开地址（实际使用时要将公开地址配置到配置到文件中）
        //这里公开地址是登录提交的地址
        if(url.indexOf("login") >= 0){
            //如果进行登录提交，则放行
            System.out.println("fangxing");
            return true;
        }
        //判断session
        HttpSession session = request.getSession();
        //从session中取得用户的身份信息
        User user = (User) session.getAttribute("user");
        if(user != null){
            //身份信息存在，放行
            return true;
        }
        //执行到这里表示用户身份需要认真，跳转到登录页面
        request.getRequestDispatcher("login.html").forward(request, response);
        System.out.println("lanjie!");
        //return false表示拦截住，不向下执行
        //return true 表示放行
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {


    }
}
