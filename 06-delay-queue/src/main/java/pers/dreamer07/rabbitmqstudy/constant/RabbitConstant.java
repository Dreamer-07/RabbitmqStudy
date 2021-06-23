package pers.dreamer07.rabbitmqstudy.constant;

/**
 * @program: RabbitmqStudy
 * @description: Rabbit 使用的常量
 * @author: EMTKnight
 * @create: 2021-06-22
 **/

public class RabbitConstant {

    //---------- 交换机
    /**
     * 正常交换机名称
     */
    public static final String EXCHANGE_DIRECT_NORMAL_X = "X";

    /**
     * 死信交换机
     */
    public static final String EXCHANGE_DIRECT_DEAD_LETTER_Y = "Y";

    /**
     * 延迟交换机
     */
    public static final String EXCHANGE_DELAYED = "delayed.exchange";

    //---------- 队列
    /**
     * 正常队列 QA
     */
    public static final String QUEUE_NORMAL_QA = "QA";

    /**
     * 正常队列 QB
     */
    public static final String QUEUE_NORMAL_QB = "QB";

    /**
     * 正常队列 QC
     */
    public static final String QUEUE_NORMAL_QC = "QC";

    /**
     * 死信队列 QD
     */
    public static final String QUEUE_DEAD_LETTER_QD = "QD";


    /**
     * 延迟队列 delayed.queue
     */
    public static final String QUEUE_DELAYED = "delayed.queue";


    //---------- routingKey
    /**
     * 维护队列 QA&QB 和 X 交换机之间的 routingKey
     */
    public static final String ROUTING_KEY_QA_X = "XA";
    public static final String ROUTING_KEY_QB_X = "XB";
    public static final String ROUTING_KEY_QC_X = "XC";
    /**
     * 维护队列 QD 和 Y 交换机之间的 routingKey
     */
    public static final String ROUTING_KEY_QD_Y = "YD";
    /**
     * 维护延迟交换机和延迟队列之间的 routingKey
     */
    public static final String ROUTING_KEY_DELAYED = "delayed.routingKey";

}
