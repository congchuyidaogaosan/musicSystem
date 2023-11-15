package com.example.musicserver.config;

import com.alibaba.fastjson.JSONArray;
import com.example.musicserver.utils.Result;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptors implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        System.out.println("进入拦截器");
        if(cookies !=null && cookies.length>0){
            for (Cookie cookie:cookies) {
                System.out.println("遍历cookie："+ cookie.getName());
                if ("isLogin".equals(cookie.getName())) {

                    return true;
                }
            }
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json;charset=utf-8");
            String result= JSONArray.toJSON(Result.error("104","登录超时请重新登录")).toString();
            response.getWriter().print(result);
        }



        return false;
    }
}
