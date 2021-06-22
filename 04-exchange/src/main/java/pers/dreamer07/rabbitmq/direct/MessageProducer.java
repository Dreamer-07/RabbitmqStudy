package pers.dreamer07.rabbitmq.direct;

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

    private final static String DIRECT_EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取通信
        Channel channel = RabbitmqUtil.getChannel();

        Scanner sc = new Scanner(System.in);
        System.out.println("输入要发送队列的 routingKey(info/warning/error)");
        // 获取要发送队列的 routingKey
        String routingKey = sc.next();
        while (sc.hasNext()) {
            String message = sc.next();
            channel.basicPublish(DIRECT_EXCHANGE_NAME, routingKey, null, message.getBytes());
            System.out.println("成功发送消息:" + message);
        }
    }
}
