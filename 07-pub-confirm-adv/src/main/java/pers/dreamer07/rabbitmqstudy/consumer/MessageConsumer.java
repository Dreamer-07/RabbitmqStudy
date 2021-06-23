package pers.dreamer07.rabbitmqstudy.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import pers.dreamer07.rabbitmqstudy.constant.RabbitmqConstant;

/**
 * @program: RabbitmqStudy
 * @description: 对消息进行消费
 * @author: EMTKnight
 * @create: 2021-06-23
 **/
@Component
@Slf4j
public class MessageConsumer {

    @RabbitListener(queues = RabbitmqConstant.QUEUE_CONFIRM)
    public void receiveConfirmQueue(Message message){
        log.info("{} 队列收到消息:{}", RabbitmqConstant.QUEUE_CONFIRM, new String(message.getBody()));
    }

    @RabbitListener(queues = RabbitmqConstant.QUEUE_WARNING_CONFIRM)
    public void receiveWarningConfirmQueue(Message message){
        log.error("{} 报警队列收到消息:{}", RabbitmqConstant.QUEUE_WARNING_CONFIRM, new String(message.getBody()));
    }
}
