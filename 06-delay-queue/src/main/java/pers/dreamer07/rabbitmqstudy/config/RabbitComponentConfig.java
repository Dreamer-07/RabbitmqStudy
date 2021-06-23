package pers.dreamer07.rabbitmqstudy.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.dreamer07.rabbitmqstudy.constant.RabbitConstant;

import java.util.HashMap;


/**
 * @program: RabbitmqStudy
 * @description: 配置 RabbitMQ 组件
 * @author: EMTKnight
 * @create: 2021-06-22
 **/
@Configuration
public class RabbitComponentConfig {

    /**
     * 配置正常交换机
     * @return
     */
    @Bean
    public DirectExchange xExchange(){
        return new DirectExchange(RabbitConstant.EXCHANGE_DIRECT_NORMAL_X);
    }

    /**
     * 配置死信交换机
     * @return
     */
    @Bean
    public DirectExchange yExchange(){
        return new DirectExchange(RabbitConstant.EXCHANGE_DIRECT_DEAD_LETTER_Y);
    }

    /**
     * 配置自定义交换机(延迟交换机)
     * @return
     */
    @Bean
    public CustomExchange delayExchange(){
        /* 自定义配置交换机
        *   - 第一个为交换机名称
        *   - 第二个为交换机类型，这里是 x-delayed-message 表示延迟交换机
        *   - 是否持久化
        *   - 是否自动删除
        *   - 其他配置 - 这里配置了该交换机与队列之间的匹配模式
        * */
        return new CustomExchange(
                RabbitConstant.EXCHANGE_DELAYED,
                "x-delayed-message",
                true, false,
                new HashMap<String, Object>(){{
                    // 配置该交换机与队列的模式
                    put("x-delayed-type", "direct");
                }}
        );
    }

    /**
     * 配置正常队列 QA
     * @return
     */
    @Bean("QA")
    public Queue qa(){
        return QueueBuilder.durable(RabbitConstant.QUEUE_NORMAL_QA)
                // 配置死信交换机
                .deadLetterExchange(RabbitConstant.EXCHANGE_DIRECT_DEAD_LETTER_Y)
                // 配置死信交换机与死信队列的 routingKey
                .deadLetterRoutingKey(RabbitConstant.ROUTING_KEY_QD_Y)
                // 配置消息 TTL(ms)
                .ttl(10000)
                .build();
    }

    /**
     * 配置正常队列 QB
     * @return
     */
    @Bean("QB")
    public Queue qb(){
        return QueueBuilder.durable(RabbitConstant.QUEUE_NORMAL_QB)
                // 配置死信交换机
                .deadLetterExchange(RabbitConstant.EXCHANGE_DIRECT_DEAD_LETTER_Y)
                // 配置死信交换机与死信队列的 routingKey
                .deadLetterRoutingKey(RabbitConstant.ROUTING_KEY_QD_Y)
                // 配置消息 TTL(ms)
                .ttl(40000)
                .build();
    }

    /**
     * 配置正常队列 QC
     * @return
     */
    @Bean("QC")
    public Queue qc(){
        return QueueBuilder.durable(RabbitConstant.QUEUE_NORMAL_QC)
                // 配置死信交换机
                .deadLetterExchange(RabbitConstant.EXCHANGE_DIRECT_DEAD_LETTER_Y)
                // 配置死信交换机与死信队列的 routingKey
                .deadLetterRoutingKey(RabbitConstant.ROUTING_KEY_QD_Y)
                .build();
    }

    /**
     * 配置死信队列 QD
     * @return
     */
    @Bean("QD")
    public Queue qd(){
        return QueueBuilder.durable(RabbitConstant.QUEUE_DEAD_LETTER_QD).build();
    }

    /**
     * 配置延迟队列
     * @return
     */
    @Bean
    public Queue delayQueue(){
        return QueueBuilder.durable(RabbitConstant.QUEUE_DELAYED).build();
    }

    /**
     * 配置 QA 队列和 X 交换机的绑定关系
     */
    @Bean
    public Binding qaToX(){
        return BindingBuilder.bind(qa()).to(xExchange()).with(RabbitConstant.ROUTING_KEY_QA_X);
    }

    /**
     * 配置 QB 队列和 X 交换机的绑定关系
     */
    @Bean
    public Binding qbToX(){
        return BindingBuilder.bind(qb()).to(xExchange()).with(RabbitConstant.ROUTING_KEY_QB_X);
    }

    /**
     * 配置 QC 队列和 X 交换机的绑定关系
     */
    @Bean
    public Binding qcToX(){
        return BindingBuilder.bind(qc()).to(xExchange()).with(RabbitConstant.ROUTING_KEY_QC_X);
    }

    /**
     * 配置 QD 队列和 Y 交换机的绑定关系
     */
    @Bean
    public Binding qdToY(){
        return BindingBuilder.bind(qd()).to(yExchange()).with(RabbitConstant.ROUTING_KEY_QD_Y);
    }

    /**
     * 绑定延迟队列和延迟交换机
     * @return
     */
    @Bean
    public Binding delayQueueToExchange(){
        return BindingBuilder.bind(delayQueue()).to(delayExchange()).with(RabbitConstant.ROUTING_KEY_DELAYED).noargs();
    }
}
