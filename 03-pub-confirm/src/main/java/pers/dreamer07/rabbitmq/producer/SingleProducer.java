package pers.dreamer07.rabbitmq.producer;

import com.rabbitmq.client.Channel;
import pers.dreamer07.rabbitmq.utils.RabbitmqUtil;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * @program: RabbitmqStudy
 * @description: 测试单个发布确认 - 672
 * @author: EMTKnight
 * @create: 2021-06-21
 **/

public class SingleProducer {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        // 随机创建一个队列名
        String queueName = UUID.randomUUID().toString();
        // 获取通信
        Channel channel = RabbitmqUtil.getChannel();
        // 配置队列
        channel.queueDeclare(queueName, false, false, false , null);
        // 开启发布确认
        channel.confirmSelect();
        // 获取开始时间
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            // 发布消息
            channel.basicPublish("", queueName, null, ("消息" + i).getBytes());
            // 等待发布确认
            boolean flag = channel.waitForConfirms();
            if (flag) {
                System.out.println("消息" + i + ": 成功发送");
            }
        }
        // 获取结束时间
        long end = System.currentTimeMillis();
        System.out.println("使用单个确认发布发送 1000 个消息，耗时:" + (end - start));
    }

}
