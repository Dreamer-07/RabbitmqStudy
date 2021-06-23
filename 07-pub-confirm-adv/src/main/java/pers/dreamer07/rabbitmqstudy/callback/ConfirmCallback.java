package pers.dreamer07.rabbitmqstudy.callback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @program: RabbitmqStudy
 * @description: 发布确认回调函数，无论消息是否达到 Exchange 都会调用
 * @author: EMTKnight
 * @create: 2021-06-23
 **/
@Component
@Slf4j
public class ConfirmCallback implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void initRabbitTemplate(){
        // 设置 rabbitTemplate 中发布确认用的回调
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }

    /**
     * 回调函数, 无论消息是否到达 exchange 都会触发的回调函数
     * @param correlationData 保存回调消息的相关信息
     * @param ack 是否到达 exchange
     * @param cause 出错的原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = correlationData != null ? correlationData.getId() : "";
        if (ack){
            log.info("id 为 {} 的消息成功被交换机接收" , id);
        } else {
            log.error("id 为 {} 的消息没能成功被交换机接收，原因为:{}", id, cause);
        }
    }

    /**
     * 当消息无法到达队列使，回调消息会调用的函数
     * @param returnedMessage
     */
    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        log.error("无法找到交换机 {} 中路由 key 为 {} 的队列，消息为: {}, 错误原因为: {}"
                , returnedMessage.getExchange(), returnedMessage.getRoutingKey()
                , new String(returnedMessage.getMessage().getBody()), returnedMessage.getReplyText()
        );
    }
}
