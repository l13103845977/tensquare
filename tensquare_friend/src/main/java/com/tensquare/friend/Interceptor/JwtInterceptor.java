package com.tensquare.friend.Interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import util.JwtUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private JwtUtil jwtUtil;
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       //全放行 解析令牌
        String header = request.getHeader("Authorization");
        if (header!=null && !"".equals(header)) {
            if (header.startsWith("Bearer ")){
           String token=header.substring(7);
               try {
                   Claims claims = jwtUtil.parseJWT(token);
                   String roles=claims.get("roles").toString();
                   if (roles!=null && roles.equals("admin")){
                       request.setAttribute("claims_admin",claims);
                   }
                   if (roles!=null && roles.equals("user")){
                       request.setAttribute("claims_user",claims);
                   }
               }catch (Exception e){
                   throw  new RuntimeException("令牌有误");
               }
            }
        }
        return true;
    }
}
