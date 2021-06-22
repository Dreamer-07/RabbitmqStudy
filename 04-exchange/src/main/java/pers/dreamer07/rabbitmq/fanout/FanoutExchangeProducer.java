package pers.dreamer07.rabbitmq.fanout;

import com.rabbitmq.client.Channel;
import pers.dreamer07.rabbitmq.utils.RabbitmqUtil;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @program: RabbitmqStudy
 * @description: 基于 fanout 交换机模式的生产者
 * @author: EMTKnight
 * @create: 2021-06-21
 **/

public class FanoutExchangeProducer {

    private final static String FANOUT_EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取通信
        Channel channel = RabbitmqUtil.getChannel();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String message = scanner.next();
            // 通过交换机'发布'消息
            channel.basicPublish(FANOUT_EXCHANGE_NAME, "", null, message.getBytes());
            System.out.println("成功发布消息:" + message);
        }

    }

}
