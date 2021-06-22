package pers.dreamer07.rabbitmq.producer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import pers.dreamer07.rabbitmq.utils.RabbitmqUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @program: RabbitmqStudy
 * @description: 消息生产者
 * @author: EMTKnight
 * @create: 2021-06-22
 **/

public class MessageProducer {

    private final static String NORMAL_EXCHANGE_NAME = "normal_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取通信
        Channel channel = RabbitmqUtil.getChannel();
        for (int i = 0; i < 10; i++) {
            String message = i + "";
            // 向交换机中发送消息
            channel.basicPublish(NORMAL_EXCHANGE_NAME, "normal.queue",
                    // 对消息的过期时间 TTL 进行配置(单位: ms)
//                    new AMQP.BasicProperties().builder().expiration("10000").build(),
                    null,
                    message.getBytes()
            );
        }

    }

}
