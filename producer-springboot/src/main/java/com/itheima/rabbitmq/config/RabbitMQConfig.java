package com.itheima.rabbitmq.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Author: IsaiahLu
 * @date: 2023/1/31 16:07
 */
@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE_NAME = "boot_topic_exchange";
    public static final String QUEUE_NAME = "boot_queue";

    /**
     * 交换机
     *
     * @return 交换机
     */
    @Bean("bootExchange")
    public Exchange bootExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME).durable(true).build();
    }

    /**
     * 队列
     *
     * @return 队列
     */
    @Bean("bootQueue")
    public Queue bootQueue() {
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    /**
     * 交换机和队列的绑定
     *
     * @return 绑定对象
     */
    @Bean
    public Binding bindingQueueExchange(@Qualifier("bootQueue") Queue queue, @Qualifier("bootExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("boot.#").noargs();

    }
}
