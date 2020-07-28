package com.tensquare.rabbit.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <h3>tensquare_parent</h3>
 * <p>发送消息</p>
 *
 * @author : lsy
 * @date : 2020-07-27 17:50
 *
 *  启动web界面 ./rabbitmq-plugins enable rabbitmq_management
 *  Direct Exchange 直连模式 bingingkey和routingkey完全匹配
 *  Topic Exchange   通配符模式    routingking和topic进行模糊匹配，*-只能匹配一个单词，#-可以匹配多个单词
 *   Fanout Exchange  订阅模式  不处理路由键，直接发送到匹配得队列
 *   Headers Exchange 已放弃
 *
 *   防止消息丢失
 *       持久化：交换器持久化，队列持久化，消息持久化
 **/
@RestController
public class SendMsgController {

    @Resource
    RabbitTemplate rabbitTemplate;

    @RequestMapping("/sendMsg/{msg}")
    public void  sendMsg(@PathVariable String msg){

        rabbitTemplate.convertAndSend("test","test",msg);
    }
    @RequestMapping("/sendMsg1/{msg}")
    public void  sendMsg1(@PathVariable String msg){
        rabbitTemplate.convertAndSend("test","test1",msg);
    }
    @RequestMapping("/sendMsg2/{msg}")
    public void  testtopic(@PathVariable String msg){
        rabbitTemplate.convertAndSend("testtopic","test.topic",msg);
    }
}
