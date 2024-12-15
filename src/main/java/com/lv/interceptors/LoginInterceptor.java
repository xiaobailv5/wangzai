package com.lv.interceptors;

import com.lv.pojo.Result;
import com.lv.utils.JwtUtil;
import com.lv.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

/**
 * @program: wangzai
 * @ClassName LoginInterceptor
 * @description: 登录拦截器
 * @author: gxjh
 * @create: 2024-12-14 19:42
 * @Version 1.0
 **/
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //令牌验证
        String token = request.getHeader("Authorization");
        //验证token
        try {
            Map<String, Object> claims = JwtUtil.parseToken(token);
            String username = (String)claims.get("username");
            ThreadLocalUtil.set(username);
            //放行
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            //不放行
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //删除线程变量中的数据，防止内存泄露
        ThreadLocalUtil.remove();
    }
}