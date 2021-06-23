package pers.dreamer07.rabbitmqstudy.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pers.dreamer07.rabbitmqstudy.constant.RabbitmqConstant;

/**
 * @program: RabbitmqStudy
 * @description:
 * @author: EMTKnight
 * @create: 2021-06-23
 **/
@Slf4j
@Controller
@RequestMapping("/msg")
public class MessageController {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @GetMapping("/send/{message}")
    public void sendConfirmMsg(@PathVariable String message){
        // 创建一个 CorrelationData 对象，里面可以配置一些消息的信息用于回调函数时使用
        CorrelationData correlationData = new CorrelationData("1");
        rabbitTemplate.convertAndSend(
            RabbitmqConstant.EXCHANGE_DIRECT_CONFIRM,
            RabbitmqConstant.ROUTING_KEY_CONFIRM + 1,
            message, correlationData
        );
        log.info("向 {} 队列发送消息:{}", RabbitmqConstant.QUEUE_CONFIRM, message);
    }

}
