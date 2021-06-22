package pers.dreamer07.rabbitmq.topic;

import com.rabbitmq.client.Channel;
import pers.dreamer07.rabbitmq.utils.RabbitmqUtil;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @program: RabbitmqStudy
 * @description: 消息生产者
 * @author: EMTKnight
 * @create: 2021-06-22
 **/

public class MessageProducer {

    private final static String TOPIC_EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取通信
        Channel channel = RabbitmqUtil.getChannel();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("请输入要发送消息队列的 routingKey:");
            String routingKey = scanner.next();
            System.out.print("请输入要发送的消息体:");
            String message = scanner.next();
            channel.basicPublish(TOPIC_EXCHANGE_NAME, routingKey, null, message.getBytes());
            System.out.println("成功发送消息:" + message + ", 使用的 routingKey:" + routingKey);
        }
    }

}
