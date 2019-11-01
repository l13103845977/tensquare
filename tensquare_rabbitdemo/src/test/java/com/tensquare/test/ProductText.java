package com.tensquare.test;

import com.tensquare.rabbit.RabbitApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitApplication.class)
public class ProductText {

    @Autowired
private RabbitTemplate rabbitTemplate;

    @Test
    public void  sendMsg(){
        rabbitTemplate.convertAndSend("itcast","直接模式生产者测试");
    }
}

