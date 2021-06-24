package pers.dreamer07.rabbitmqstudy.controller;

import com.rabbitmq.client.AMQP;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pers.dreamer07.rabbitmqstudy.config.RabbitComponentConfig;

/**
 * @program: RabbitmqStudy
 * @description:
 * @author: EMTKnight
 * @create: 2021-06-24
 **/
@Controller
@Slf4j
@RequestMapping("/msg")
public class MessageController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/send/{message}/{priority}")
    public void sendMsg(@PathVariable String message, @PathVariable Integer priority){
        rabbitTemplate.convertAndSend("", RabbitComponentConfig.QUEUE_Priority,
                message, msg -> {
                    // 设置消息的优先级
                    msg.getMessageProperties().setPriority(priority);
                    return msg;
                }
        );
        log.info("成功发送消息");
    }

}
