package pers.dreamer07.rabbitmq.consumer;

import com.rabbitmq.client.*;
import pers.dreamer07.rabbitmq.utils.RabbitmqUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @program: RabbitmqStudy
 * @description: 死信消息消费者
 * @author: EMTKnight
 * @create: 2021-06-22
 **/

public class DeadLetterMessageConsumer {

    private final static String DEAD_LETTER_EXCHANGE_NAME = "dead_exchange";
    private final static String DEAD_QUEUE = "dead-queue";
    private static Channel channel;

    static {
        // 获取信道
        try {
            channel = RabbitmqUtil.getChannel();
            // 声明交换机
            channel.exchangeDeclare(DEAD_LETTER_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            // 声明队列
            channel.queueDeclare(DEAD_QUEUE, false, false, false, null);
            // 绑定队列和交换机
            channel.queueBind(DEAD_QUEUE, DEAD_LETTER_EXCHANGE_NAME, "dead.letter");
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("等待死信消息.....");
        channel.basicConsume(DEAD_QUEUE, true,
                (tag, message) -> System.out.println(DEAD_QUEUE + "处理死信消息:" + new String(message.getBody())),
                (message) -> {}
        );
    }
}
