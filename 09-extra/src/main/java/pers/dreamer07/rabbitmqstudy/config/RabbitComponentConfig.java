package pers.dreamer07.rabbitmqstudy.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: RabbitmqStudy
 * @description:
 * @author: EMTKnight
 * @create: 2021-06-24
 **/
@Configuration
public class RabbitComponentConfig {

    public final static String QUEUE_Priority = "pir.queue";

    @Bean
    public Queue priQueue(){
        /*
        * maxPriority(int num): 指定该队列的最大优先级
        *   - 允许的最大值是 255，这里指定为 10
        * */
        return QueueBuilder.durable(QUEUE_Priority).maxPriority(10).build();
    }

}
