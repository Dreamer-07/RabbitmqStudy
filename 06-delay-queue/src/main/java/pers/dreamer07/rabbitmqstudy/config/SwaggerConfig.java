package pers.dreamer07.rabbitmqstudy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

/**
 * @program: RabbitmqStudy
 * @description:
 * @author: EMTKnight
 * @create: 2021-06-22
 **/
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo()).groupName("06-delay-queue");
    }

    /**
     * 配置 API 接口文档信息
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "SpringBoot 整合 RabbitMQ",
                "通过 Swagger 提供 API 接口实现对延迟队列的测试",
                "1.0",
                "http://www.baidu.com", // 网址?
                new Contact("Prover", "http://www.baidu.com", "111222@qq.com"),
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>()
        );
    }

}
