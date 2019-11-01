package com.tensquare.user.Interceptor;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtil jwtUtil;
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        ConnectionFactory factory = new ConnectionFactory();

        Connection conn = factory.newConnection();
// 创建信道
        Channel channel = conn.createChannel();
// 声明队列
        channel.queueDeclare("", false, false, false, null);
// 开启发送方确认模式
        channel.confirmSelect();
        for (int i = 0; i < 10; i++) {
            String message = String.format("时间 => %s", new Date().getTime());
            channel.basicPublish("", "", null, message.getBytes("UTF-8"));
        }
        channel.waitForConfirmsOrDie(); //直到所有信息都发布，只要有一个未确认就会IOException
        System.out.println("全部执行完成");
        //全放行 解析令牌
        System.out.println("用户登录经过了拦截器");
        String header = request.getHeader("Authorization");
        if (header!=null && !"".equals(header)) {
            if (header.startsWith("Bearer ")){
           String token=header.substring(7);
               try {
                   Claims claims = jwtUtil.parseJWT(token);
                   String roles=claims.get("roles").toString();
                   System.out.println(roles+"拦截器角色");
                   if (roles!=null && roles.equals("admin")){
                       request.setAttribute("claims_admin",token);
                       request.setAttribute("aa","aa");
                   }
                   if (roles!=null && roles.equals("user")){
                       request.setAttribute("claims_user",token);
                   }
               }catch (Exception e){
                   throw  new RuntimeException("令牌有误");
               }
            }
        }
        return true;
    }
}
