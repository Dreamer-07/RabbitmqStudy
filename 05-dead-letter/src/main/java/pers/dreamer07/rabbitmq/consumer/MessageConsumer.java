package pers.dreamer07.rabbitmq.consumer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import pers.dreamer07.rabbitmq.utils.RabbitmqUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

/**
 * @program: RabbitmqStudy
 * @description: 消息消费者
 * @author: EMTKnight
 * @create: 2021-06-22
 **/

public class MessageConsumer {

    private final static String DEAD_LETTER_EXCHANGE_NAME = "dead_exchange";

    private final static String NORMAL_EXCHANGE_NAME = "normal_exchange";
    private final static String NORMAL_QUEUE_NAME = "normal_queue";
    private static Channel channel;

    static {
        // 获取通信
        try {
            channel = RabbitmqUtil.getChannel();
            // 配置交换机
            channel.exchangeDeclare(NORMAL_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            // 配置队列
            channel.queueDeclare(NORMAL_QUEUE_NAME, false, false, false, new HashMap<String,Object>(){{
                // 配置队列参数
                // 1. 配置死信交换机
                put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE_NAME);
                // 2. 配置死信交换队列的路由 key
                put("x-dead-letter-routing-key", "dead.letter");
                // 3. [可选] 配置消息 TTL(存活时间，也可以由客户端指定) ms
//            put("x-message-ttl", 10000);
                // 4. [模拟队列达到最大长度] 配置队列内消息最大个数
//                put("x-max-length", 6);
            }});
            // 绑定队列和交换机
            channel.queueBind(NORMAL_QUEUE_NAME, NORMAL_EXCHANGE_NAME, "normal.queue");
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("等待消息....");
//        channel.basicConsume(NORMAL_QUEUE_NAME, true,
//                (tag, message) -> System.out.println(NORMAL_QUEUE_NAME + "处理消息:" + new String(message.getBody())),
//                (message) -> {}
//        );
        // 测试拒绝处理指定消息并且不重新加入到消息队列中, 关闭自动应答
        channel.basicConsume(NORMAL_QUEUE_NAME, false,
                (tag, message) -> {
                    String msg = new String(message.getBody());
                    // 判断字符串
                    if ("5".equals(msg)) {
                        System.out.println(NORMAL_QUEUE_NAME + "拒绝处理消息:" + new String(message.getBody()));
                        // 拒绝处理消息，第二个参数设置为 false 表示不重新返回消息队列中(但可以被转发到死信队列中)
                        channel.basicReject(message.getEnvelope().getDeliveryTag(), false);
                    } else {
                        System.out.println(NORMAL_QUEUE_NAME + "处理消息:" + new String(message.getBody()));
                        // 手动应答
                        channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
                    }
                },
                (message) -> {}
       );
    }
}
