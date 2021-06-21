package pers.dreamer07.rabbitmq.answer;

import com.rabbitmq.client.Channel;
import pers.dreamer07.rabbitmq.utils.RabbitmqUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @program: RabbitmqStudy
 * @description: 测试消息的手动应答, 消息是否可以重新入队
 * @author: EMTKnight
 * @create: 2021-06-21
 **/

public class MessageAnswerWorker02 {

    // 定义队列名
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitmqUtil.getChannel();
        // 设置不公平分发，默认是 0 (轮询分发)
        channel.basicQos(1);
        // 设置第二个参数取消自动应答
        channel.basicConsume(QUEUE_NAME, false,
                (consumerTag, message) -> {
                    System.out.println(consumerTag + ": 成功接收到消息 - " + new String(message.getBody()));
                    // 模拟业务处理
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    /*
                    * basicAck: 手动应答
                    *   - 第一个参数为消息表示
                    *   - 第二个参数为是否批量应答
                    * */
                    channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
                },
                (consumerTag) -> System.out.println(consumerTag + ": 接收消息的过程中出现错误")
        );
    }

}
