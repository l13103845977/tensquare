package com.tensquare.sms.listener;

import com.tensquare.sms.utils.SendSmsEmail;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "sms")
public class SmsLitener {

    @Autowired
    private SendSmsEmail sendSmsEmail;

    @RabbitHandler
    public void excuteSms(Map<String,String> map){
        System.out.println("注册账号消息队列sms");
        System.out.println(map.get("mobile"));
        System.out.println(map.get("checkcode"));
        try {
            sendSmsEmail.sendEmail(map.get("mobile"),"验证码","你本次注册账号的验证码为："+map.get("checkcode"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
