package com.itheima.consumerspringboot;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消费者监听器
 * @Author: IsaiahLu
 * @date: 2023/1/31 16:59
 */
@Component
public class RabbitMQListner {
    @RabbitListener(queues = "boot_queue")
    public void ListenerQueue(Message message){
        System.out.println("监听到的消息："+new String(message.getBody()));

    }


}
