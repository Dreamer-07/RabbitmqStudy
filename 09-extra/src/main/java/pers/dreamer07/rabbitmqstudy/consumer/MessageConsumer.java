package pers.dreamer07.rabbitmqstudy.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import pers.dreamer07.rabbitmqstudy.config.RabbitComponentConfig;

/**
 * @program: RabbitmqStudy
 * @description:
 * @author: EMTKnight
 * @create: 2021-06-24
 **/
@Component
@Slf4j
public class MessageConsumer {

    @RabbitListener(queues = RabbitComponentConfig.QUEUE_Priority)
    public void receivePriQueue(Message message){
        log.info("优先级队列收到消息:{}, 优先级为: {}",
                new String(message.getBody()),
                message.getMessageProperties().getPriority()
        );
    }

}
