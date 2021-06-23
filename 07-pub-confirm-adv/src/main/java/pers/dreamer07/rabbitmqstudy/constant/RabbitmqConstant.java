package pers.dreamer07.rabbitmqstudy.constant;

/**
 * @program: RabbitmqStudy
 * @description: 定义 RabbitMQ 组件中使用的常量
 * @author: EMTKnight
 * @create: 2021-06-23
 **/

public class RabbitmqConstant {

    public final static String EXCHANGE_DIRECT_CONFIRM = "confirm.exchange";
    public final static String EXCHANGE_FANOUT_BACKUP_CONFIRM = "backup.confirm.exchange";

    public final static String QUEUE_CONFIRM = "confirm.queue";
    public final static String QUEUE_BACKUP_CONFIRM = "backup.confirm.queue";
    public final static String QUEUE_WARNING_CONFIRM = "warning.confirm.queue";

    public final static String ROUTING_KEY_CONFIRM = "confirm";
}
