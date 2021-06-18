package pers.dreamer07.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sun.org.apache.xml.internal.security.algorithms.MessageDigestAlgorithm;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

/**
 * @program: RabbitmqStudy
 * @description: 消息消费者
 * @author: EMTKnight
 * @create: 2021-06-18
 **/

public class MessageConsumer {

    // 定义队列名
    private static final String QUEUE_NAME = "hello";
    // 定义主机地址
    private static final String HOST = "192.168.127.139";
    // 定义连接 RabbitMQ Server 用户名
    private static final String USER_NAME = "prover";
    // 定义连接 RabbitMQ Server 密码
    private static final String PASSWORD = "123456";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置主键地址，用户名和免密
        factory.setHost(HOST);
        factory.setUsername(USER_NAME);
        factory.setPassword(PASSWORD);
        // 创建连接
        Connection connection = factory.newConnection();
        // 获取通信(Conn 内部的逻辑连接)
        Channel channel = connection.createChannel();
        /* 通过通信获取消息
        *   1. 消息队列
        *   2. 消费成功后是否自动答应
        *   3. 消息送达时通知的回调接口
        *   4. 消费者取消消费的回调接口
        * */
        channel.basicConsume(QUEUE_NAME, true,
                (consumerTag, message) -> System.out.println(consumerTag + ":" + new String(message.getBody())),
                (consumerTag) -> System.out.println("消息消费失败:" + consumerTag)
        );
    }

}
