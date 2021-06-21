package pers.dreamer07.rabbitmq.worker;

import com.rabbitmq.client.Channel;
import pers.dreamer07.rabbitmq.utils.RabbitmqUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @program: RabbitmqStudy
 * @description: 工作线程(消费者)
 * @author: EMTKnight
 * @create: 2021-06-21
 **/
public class MessageWorker {

    // 定义队列名
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitmqUtil.getChannel();
        channel.basicConsume(QUEUE_NAME, true,
                (consumerTag, message) -> System.out.println(consumerTag + ": 成功接收到消息 - " + new String(message.getBody())),
                (consumerTag) -> System.out.println(consumerTag + ": 接收消息的过程中出现错误")
        );
    }

}
