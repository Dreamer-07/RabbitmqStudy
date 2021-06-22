package pers.dreamer07.rabbitmq.fanout;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Delivery;
import pers.dreamer07.rabbitmq.utils.RabbitmqUtil;
import sun.plugin2.message.Message;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @program: RabbitmqStudy
 * @description: 基于 fanout 交换机模式的消费者
 * @author: EMTKnight
 * @create: 2021-06-21
 **/

public class FanoutExchangeConsumer {

    private final static String FANOUT_EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取连接
        Channel channel = RabbitmqUtil.getChannel();
        // 声明交换机
        channel.exchangeDeclare(FANOUT_EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        // 声明临时队列
        String queueName = channel.queueDeclare().getQueue();
        /* 将交换机和路由进行绑定
        *   - 第一个参数为队列名
        *   - 第二个参数为交换机名
        *   - 第三个参数为维护关系的路由 key
        * */
        channel.queueBind(queueName, FANOUT_EXCHANGE_NAME, "");
        System.out.println("正在等待新消息的到来....");

        // 接收消息
        channel.basicConsume(queueName, true,
            (consumerTag, message) -> System.out.println("成功接收到消息:" + new String(message.getBody())),
            (consumerTag) -> System.out.println("没有成功接收到消息:" + consumerTag)
        );
    }

}
