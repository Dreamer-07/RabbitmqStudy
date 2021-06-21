package pers.dreamer07.rabbitmq.producer;

import com.rabbitmq.client.Channel;
import pers.dreamer07.rabbitmq.utils.RabbitmqUtil;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeoutException;

/**
 * @program: RabbitmqStudy
 * @description: 异步发布确认 - 43
 * @author: EMTKnight
 * @create: 2021-06-21
 **/

public class AsyncProducer {

    public static void main(String[] args) throws IOException, TimeoutException {
        // 随机创建一个队列名
        String queueName = UUID.randomUUID().toString();
        // 获取通信
        Channel channel = RabbitmqUtil.getChannel();
        // 配置队列
        channel.queueDeclare(queueName, false, false, false , null);
        // 开启发布确认
        channel.confirmSelect();
        // 创建同步容器
        ConcurrentSkipListMap<Long, String> dataMap = new ConcurrentSkipListMap<>();
        // 获取开始时间
        long start = System.currentTimeMillis();

        /*
        * 添加异步确认发布回调函数
        *   第一个参数为成功发布的回调函数
        *   第二个参数为发布失败的回调函数
        *   * 两个参数为同一个函数式接口的实现类
        *       - 第一个参数是消息的序列号(标识)
        *       - 第二个参数是消息是否为批量确认
        * */
        channel.addConfirmListener(
                (deliveryTag, multiple) -> {
                    // 判断是否为批量确认
                    if (multiple) {
                        dataMap.headMap(deliveryTag).clear();
                    } else {
                        // 从容器中删除对应的消息
                        dataMap.headMap(deliveryTag);
                    }
                    System.out.println(deliveryTag + "消息发布确认成功");
                },
                (deliveryTag, multiple) -> {
                    String message = dataMap.get(deliveryTag);
                    System.out.println(deliveryTag + "消息发布确认失败, 具体的消息体为:" + message);
                }
        );

        for (int i = 1; i <= 1000; i++) {
            // 发布消息
            channel.basicPublish("", queueName, null, ("消息" + i).getBytes());
            // 将消息和对应的序列号保存到容器中
            dataMap.put(channel.getNextPublishSeqNo(), ("消息" + i));
        }

        // 获取结束时间
        long end = System.currentTimeMillis();
        System.out.println("使用异步确认发布发送 1000 个消息，耗时:" + (end - start));
    }

}
