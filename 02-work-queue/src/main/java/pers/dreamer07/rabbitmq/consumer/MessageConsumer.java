package pers.dreamer07.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import pers.dreamer07.rabbitmq.utils.RabbitmqUtil;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @program: RabbitmqStudy
 * @description: 消息生产者
 * @author: EMTKnight
 * @create: 2021-06-21
 **/

public class MessageConsumer {

    // 定义队列名
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取信道
        Channel channel = RabbitmqUtil.getChannel();
        // 配置队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 从控制台中接收消息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            // 获取消息
            String message = scanner.next();
            // 发送消息
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("消息发送成功:" + message);
        }
    }

}
