package pers.dreamer07.rabbitmqstudy.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.dreamer07.rabbitmqstudy.constant.RabbitConstant;
import pers.dreamer07.rabbitmqstudy.constant.TtlConstant;

import java.util.Date;

/**
 * @program: RabbitmqStudy
 * @description: 消息生产者(控制器)
 * @author: EMTKnight
 * @create: 2021-06-22
 **/
@RestController
@Slf4j
@RequestMapping("/msg")
public class MessageController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     * @param message
     */
    @GetMapping("/send/{message}")
    public void sendMsg(@PathVariable String message){
        /* 通过 Spring 整合的操作类 rabbitTemplate 实现消息发布
        *   - 第一个参数为交换机名称，routingKey
         *   - 第二个参数为对应死信队列的
         *   - 第三个参数为对应的消息体
        * */
        rabbitTemplate.convertAndSend(
                RabbitConstant.EXCHANGE_DIRECT_NORMAL_X, RabbitConstant.ROUTING_KEY_QA_X, "来自队列 QA 的消息:" + message);
        rabbitTemplate.convertAndSend(
                RabbitConstant.EXCHANGE_DIRECT_NORMAL_X, RabbitConstant.ROUTING_KEY_QB_X, "来自队列 QB 的消息:" + message);
    }

    /**
     * 发送消息
     * @param message
     */
    @GetMapping("/send/qc/{message}")
    public void sendMsgForQc(@PathVariable String message){
        /* 通过 Spring 整合的操作类 rabbitTemplate 实现消息发布
         *   - 第一个参数为交换机名称，
         *   - 第二个参数为对应死信队列的 routingKey
         *   - 第三个参数为对应的消息体
         * */
        rabbitTemplate.convertAndSend(
                RabbitConstant.EXCHANGE_DIRECT_NORMAL_X, RabbitConstant.ROUTING_KEY_QC_X, "来自队列 QC 的消息:" + message,
                // 配置消息的额外参数
                msg -> {
                    msg.getMessageProperties().setExpiration(TtlConstant.SEND_MSG_TTL);
                    return msg;
                }
        );
    }

    /**
     * 基于插件实现延迟队列
     * @param message 消息体
     * @param ttl 自定义过期时间
     */
    @GetMapping("/send/delay/{message}/{ttl}")
    public void sendMesForDelayQueue(@PathVariable String message, @PathVariable Integer ttl){
        log.info("于 {} 向延迟队列 - {} 中发送一条 TTL 为 {} 毫秒的消息: {}",
                new Date().toString(), RabbitConstant.QUEUE_DELAYED, ttl, message);
        rabbitTemplate.convertAndSend(
                RabbitConstant.EXCHANGE_DELAYED, RabbitConstant.ROUTING_KEY_DELAYED,
                message, msg -> {
                    // 设置过期时长(毫秒)
                    msg.getMessageProperties().setDelay(ttl);
                    return msg;
                }
        );
    }

}
