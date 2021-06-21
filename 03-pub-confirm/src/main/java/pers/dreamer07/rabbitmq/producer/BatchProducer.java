package pers.dreamer07.rabbitmq.producer;

import com.rabbitmq.client.Channel;
import pers.dreamer07.rabbitmq.utils.RabbitmqUtil;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * @program: RabbitmqStudy
 * @description: 批量确认发布 - 109
 * @author: EMTKnight
 * @create: 2021-06-21
 **/

public class BatchProducer {

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

        for (int i = 1; i <= 1000; i++) {
            // 发布消息
            channel.basicPublish("", queueName, null, ("消息" + i).getBytes());
            // 发布确认
            if (i % 100 == 0) {
                boolean flag = channel.waitForConfirms();
                if (flag) {
                    System.out.println("批量发布确认成功");
                } else {
                    System.out.println("批量发布确认失败");
                }
            }
        }

        // 获取结束时间
        long end = System.currentTimeMillis();
        System.out.println("使用批量确认发布发送 1000 个消息，耗时:" + (end - start));
    }

}
