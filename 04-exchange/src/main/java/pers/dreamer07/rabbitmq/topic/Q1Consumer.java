package pers.dreamer07.rabbitmq.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import pers.dreamer07.rabbitmq.utils.RabbitmqUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @program: RabbitmqStudy
 * @description: 基于 topic 交换机模式的 Q1 队列的消费者
 * @author: EMTKnight
 * @create: 2021-06-22
 **/

public class Q1Consumer {

    private final static String TOPIC_EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取通信
        Channel channel = RabbitmqUtil.getChannel();
        // 配置交换机
        channel.exchangeDeclare(TOPIC_EXCHANGE_NAME, BuiltinExchangeType.TOPIC, false, false, null);
        // 配置队列
        channel.queueDeclare("Q1", false, false, false, null);
        // 绑定队列和交换机
        channel.queueBind("Q1", TOPIC_EXCHANGE_NAME, "*.orange.*");
        // 等待接收消息
        channel.basicConsume("Q1", true,
                (consumerTag, message) -> {
                    System.out.println("Q1 队列中获取信息: " + new String(message.getBody()) + "，对应的 routingKey 为:" + message.getEnvelope().getRoutingKey());
                },
                (consumerTag) -> System.out.println("console --> 没有成功接收到消息:" + consumerTag)
        );
    }

}
