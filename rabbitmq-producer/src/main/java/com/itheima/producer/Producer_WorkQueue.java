package com.itheima.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: IsaiahLu
 * @date: 2023/1/20 17:56
 * TODO 生产者
 */
public class Producer_WorkQueue {

    public static void main(String[] args) throws IOException, TimeoutException {

        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置参数
        factory.setHost("121.40.160.237");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("123");
        //创建连接
        Connection connection = factory.newConnection();
        //创建Channel
        Channel channel = connection.createChannel();
        //创建队列Queue
        channel.queueDeclare("hello_world", true, false, false, null);
        //发送消息
        String body = "";
        channel.basicPublish("", "hello_world", null, body.getBytes());
        System.out.println("生产者发送消息成功！！");
        // channel.close();
        // connection.close();

    }
}
