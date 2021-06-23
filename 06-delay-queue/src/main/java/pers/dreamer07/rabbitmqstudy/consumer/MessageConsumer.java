package pers.dreamer07.rabbitmqstudy.consumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import pers.dreamer07.rabbitmqstudy.constant.RabbitConstant;

import java.util.Date;

/**
 * @program: RabbitmqStudy
 * @description: 消息消费者
 * @author: EMTKnight
 * @create: 2021-06-22
 **/
@Component
@Slf4j
public class MessageConsumer {

    /**
     * 监听死信队列 QD 中的消息
     * @param message 消息体
     * @param channel 信道
     */
    @RabbitListener(queues = RabbitConstant.QUEUE_DEAD_LETTER_QD)
    public void receiveQd(Message message, Channel channel){
        log.info("死信队列 {}, 收到死信消息为: {}", RabbitConstant.QUEUE_DEAD_LETTER_QD, new String(message.getBody()));
    }

    @RabbitListener(queues = RabbitConstant.QUEUE_DELAYED)
    public void receiveDelayQueue(Message message, Channel channel){
        log.info("延迟队列 {} 于 {} 收到延迟消息为: {}",
                RabbitConstant.QUEUE_DELAYED, new Date().toString(), new String(message.getBody())
        );
    }

}
