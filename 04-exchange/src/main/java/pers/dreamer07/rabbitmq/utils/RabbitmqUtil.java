package pers.dreamer07.rabbitmq.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @program: RabbitmqStudy
 * @description: Rabbitmq 工具类
 * @author: EMTKnight
 * @create: 2021-06-21
 **/

public class RabbitmqUtil {

    // 定义主机地址
    private static final String HOST = "192.168.127.139";
    // 定义连接 RabbitMQ Server 用户名
    private static final String USER_NAME = "prover";
    // 定义连接 RabbitMQ Server 密码
    private static final String PASSWORD = "123456";

    /**
     * 返回与 Rabbitmq Server 连接的 Channel
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    public static Channel getChannel() throws IOException, TimeoutException {
        // 创建一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置主键地址，用户名和免密
        factory.setHost(HOST);
        factory.setUsername(USER_NAME);
        factory.setPassword(PASSWORD);
        // 创建连接
        Connection connection = factory.newConnection();
        // 获取通信(Conn 内部的逻辑连接)
        return connection.createChannel();
    }

}
