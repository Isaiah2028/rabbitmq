package com.itheima.producer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.security.cert.CertPathBuilderException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: IsaiahLu
 * @date: 2023/1/20 17:56
 * TODO 生产者
 */
public class Producer_PubSub {

    public static void main(String[] args) throws IOException, TimeoutException {

        //1创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //2设置参数
        factory.setHost("121.40.160.237");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("123");
        //3创建连接
        Connection connection = factory.newConnection();
        //4创建Channel
        Channel channel = connection.createChannel();
        //5创建交换机 exchange
        /*
          Exchange.DeclareOk exchangeDeclare(String exchange, BuiltinExchangeType type, boolean durable, boolean autoDelete, boolean internal, Map<String, Object> arguments)
          exchange:
          type:
            direct 定向
            fanout 广播
            topic
            headers
          durable 是否持久化
          autoDelete  自动删除
          internal  内部使用
          argument：参数
          */
        String exchangeName = "test_fanout";
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT, true, false, false, null);
        //6创建队列两个Queue
        String queue1Name = "test_fanout_queue1";
        String queue2Name = "test_fanout_queue2";
        channel.queueDeclare(queue1Name, true, false, false, null);
        channel.queueDeclare(queue2Name, true, false, false, null);
        //7绑定交换机和队列
/*
        queueBind(String queue, String exchange, String routingKey)
*/
        channel.queueBind(queue1Name,exchangeName,"");
        channel.queueBind(queue2Name,exchangeName,"");
        // 8发送消息
         //8.1 声明数据
        String body ="日志信息：张三调用了findAll方法...日志级别：info...";
        channel.basicPublish(exchangeName,"",null,body.getBytes());
        //9关闭资源
        channel.close();
        connection.close();

    }
}
