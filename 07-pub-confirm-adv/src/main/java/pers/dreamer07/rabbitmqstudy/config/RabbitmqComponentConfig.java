package pers.dreamer07.rabbitmqstudy.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.dreamer07.rabbitmqstudy.constant.RabbitmqConstant;

/**
 * @program: RabbitmqStudy
 * @description: 定义 RabbitMQ 组件
 * @author: EMTKnight
 * @create: 2021-06-23
 **/
@Configuration
public class RabbitmqComponentConfig {

    @Bean
    public DirectExchange confirmExchange(){
//        return new DirectExchange(RabbitmqConstant.EXCHANGE_DIRECT_CONFIRM);
        return ExchangeBuilder.directExchange(RabbitmqConstant.EXCHANGE_DIRECT_CONFIRM)
                // alternate(String exchange) 配置备份交换机
                .alternate(RabbitmqConstant.EXCHANGE_FANOUT_BACKUP_CONFIRM).build();
    }

    /**
     * 配置备份交换机
     * @return
     */
    @Bean
    public FanoutExchange backupConfirmExchange(){
        return new FanoutExchange(RabbitmqConstant.EXCHANGE_FANOUT_BACKUP_CONFIRM);
    }

    @Bean
    public Queue confirmQueue(){
        return QueueBuilder.durable(RabbitmqConstant.QUEUE_CONFIRM).build();
    }

    /**
     * 配置备份队列
     * @return
     */
    @Bean
    public Queue backupConfirmQueue(){
        return QueueBuilder.durable(RabbitmqConstant.QUEUE_BACKUP_CONFIRM).build();
    }

    /**
     * 配置报警队列
     * @return
     */
    @Bean
    public Queue warningConfirmQueue(){
        return QueueBuilder.durable(RabbitmqConstant.QUEUE_WARNING_CONFIRM).build();
    }

    @Bean
    public Binding confirmBinding(){
        return BindingBuilder.bind(confirmQueue()).to(confirmExchange()).with(RabbitmqConstant.ROUTING_KEY_CONFIRM);
    }

    /**
     * 绑定备份队列和交换机
     * @return
     */
    @Bean
    public Binding backupQueueToExchangeBinding(){
        return BindingBuilder.bind(backupConfirmQueue()).to(backupConfirmExchange());
    }

    /**
     * 绑定报警队列和交换机
     * @return
     */
    @Bean
    public Binding warningQueueToExchangeBinding(){
        return BindingBuilder.bind(warningConfirmQueue()).to(backupConfirmExchange());
    }
}
