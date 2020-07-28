package com.tensquare.rabbit.com.tensquare.rabbit.customer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 安装rabbitmq  https://www.cnblogs.com/lgq168/p/12669167.html
 * AMQP:高级消息队列协议
 */
@Component
public class Customer {

    @RabbitHandler
    @RabbitListener(queues = "test")
    public  void  getMsg(String msg){

        System.out.println("直接模式消费消息"+msg);
    }

    @RabbitHandler
    @RabbitListener(queues = "test")
    public  void  getMsg1(String msg){
        System.out.println("直接模式消费消息test1"+msg);
    }

    @RabbitHandler
    @RabbitListener(queues = "testtopic")
    public  void  testtopic(String msg){
        System.out.println("通配符testtopic"+msg);
    }
}
