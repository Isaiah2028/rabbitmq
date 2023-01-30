package com.itheima.producer;

import com.rabbitmq.client.BuiltinExchangeType;
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
public class Producer_Routing {

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
        String exchangeName = "test_direct";
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT, true, false, false, null);
        //6创建队列两个Queue
        String queue1Name = "test_direct_queue1";
        String queue2Name = "test_direct_queue2";
        channel.queueDeclare(queue1Name, true, false, false, null);
        channel.queueDeclare(queue2Name, true, false, false, null);
        //7绑定交换机和队列
/*
        queueBind(String queue, String exchange, String routingKey)
*/
        //队列1绑定 绑定error信息
        channel.queueBind(queue1Name, exchangeName, "error");
        //队列2绑定 绑定info，warning，error信息
        channel.queueBind(queue2Name, exchangeName, "info");
        channel.queueBind(queue2Name, exchangeName, "error");
        channel.queueBind(queue2Name, exchangeName, "warning");
        // 8发送消息
        //8.1 声明数据
        // String body ="日志信息：张三调用了findAll方法...日志级别：info...";
        String body = "日志信息：张三调用了delete方法..报错了....日志级别：error...";
        channel.basicPublish(exchangeName, "error", null, body.getBytes());
        //9关闭资源
        channel.close();
        connection.close();

    }
}
