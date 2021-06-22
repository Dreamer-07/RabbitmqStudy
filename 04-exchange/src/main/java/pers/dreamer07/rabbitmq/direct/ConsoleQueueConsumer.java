package pers.dreamer07.rabbitmq.direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import pers.dreamer07.rabbitmq.utils.RabbitmqUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @program: RabbitmqStudy
 * @description: 基于 direct 交换机模式 console 队列的消费者
 * @author: EMTKnight
 * @create: 2021-06-21
 **/

public class ConsoleQueueConsumer {

    private final static String DIRECT_EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取通信
        Channel channel = RabbitmqUtil.getChannel();
        // 配置交换机
        channel.exchangeDeclare(DIRECT_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        // 配置队列
        channel.queueDeclare("console", false, false, false, null);
        // 绑定队列和交换机
        channel.queueBind("console", DIRECT_EXCHANGE_NAME, "info");
        channel.queueBind("console", DIRECT_EXCHANGE_NAME, "warning");
        System.out.println("正在等待新消息的到来....");

        // 接收消息
        channel.basicConsume("console", true,
                (consumerTag, message) -> System.out.println("console --> 成功接收到消息:" + new String(message.getBody())),
                (consumerTag) -> System.out.println("console --> 没有成功接收到消息:" + consumerTag)
        );
    }

}
