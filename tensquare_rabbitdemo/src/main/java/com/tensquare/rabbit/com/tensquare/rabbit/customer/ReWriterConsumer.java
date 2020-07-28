package com.tensquare.rabbit.com.tensquare.rabbit.customer;

import com.rabbitmq.client.*;
import lombok.SneakyThrows;

import java.io.IOException;

/**
 * <h3>tensquare_parent</h3>
 * <p>重写consumer</p>
 *
 * @author : lsy
 * @date : 2020-07-28 14:36
 **/
public class ReWriterConsumer extends DefaultConsumer {
    public ReWriterConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleConsumeOk(String s) {

    }

    @Override
    public void handleCancelOk(String s) {

    }

    @Override
    public void handleCancel(String s) throws IOException {

    }

    @Override
    public void handleShutdownSignal(String s, ShutdownSignalException e) {

    }

    @Override
    public void handleRecoverOk(String s) {

    }

    @SneakyThrows
    @Override
    public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {
        String msg = new String(bytes, "uts-8");
        Thread.sleep(1000);
        System.out.println(msg);
    }
}
