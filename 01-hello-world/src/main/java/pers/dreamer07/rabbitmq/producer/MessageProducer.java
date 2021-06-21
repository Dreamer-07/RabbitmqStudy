package pers.dreamer07.rabbitmq.producer;

import com.rabbitmq.client.*;
import sun.security.ssl.HandshakeOutStream;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @program: RabbitmqStudy
 * @description: 消息生产者
 * @author: EMTKnight
 * @create: 2021-06-18
 **/

public class MessageProducer {

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
        /* 创建队列
        *   1. 队列名
        *   2. 队列中的消息是否实现持久化，默认存储在内存中
        *   3. 队列是否进行消费共享, true 表示多个消费者共享，false 表示只供一个消费者消费
        *   4. 是否自动删除，最后一个消费者服务器断开连接后，是否自动删除该队列
        *   5. 自动删除
        * */
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        // 定义消费信息
        String message = "hello world";
        /* 利用信道发送消息
        *   1. 交换机名，默认使用空字符串即可
        *   2. 路由 key，这里使用队列名即可
        *   3. 其他参数
        *   4. 消息
        * */
        channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        System.out.println("消息发送完毕");
    }

}
