package com.itheima.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: IsaiahLu
 * @date: 2023/1/20 18:38
 * TODO 消费者
 */
public class Consumer_Queues1 {
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
        channel.queueDeclare("queues", true, false, false, null);

        //接收消息
        Consumer consumer = new DefaultConsumer(channel){
            /**
             * 回调方法，方法收到消息后会自动执行该方法
             * @param consumerTag 消息标识
             * @param envelope  获取一些信息，交换机，路由key。。。
             * @param properties 配置信息
             * @param body  数据
             * @throws IOException  异常抛出
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
/*                System.out.println("consumerTag=={}"+consumerTag);
                System.out.println("Exchange=={}"+envelope.getExchange());
                System.out.println("RoutingKey=={}"+envelope.getRoutingKey());
                System.out.println("properties=={}"+properties);*/
                System.out.println("body=={}"+new String(body));

            }
        };
        channel.basicConsume("queues",true,consumer);

        //监听消息，不关闭资源


    }
}
