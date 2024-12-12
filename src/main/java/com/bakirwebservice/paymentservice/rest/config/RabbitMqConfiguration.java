package com.bakirwebservice.paymentservice.rest.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {

    @Value("${sample.rabbitmq.exchange}")
    private String exchange;

    @Bean
    DirectExchange exchange(){
        return new DirectExchange(exchange);
    }

    @Bean
    Queue firstStepCartCheckout() {
        return new Queue("firstStepCartCheckout", true);
    }

    @Bean
    Queue secondStepCartCheckout() {
        return new Queue("secondStepCartCheckout", true);
    }

    @Bean
    Queue thirdStepCartCheckout() {
        return new Queue("thirdStepCartCheckout", true);
    }


    @Bean
    Binding firstCartCheckoutBinding(){
        return BindingBuilder.bind(firstStepCartCheckout()).to(exchange()).with("firstStepCartCheckout");
    }

    @Bean
    Binding secondCartCheckoutBinding(){
        return BindingBuilder.bind(secondStepCartCheckout()).to(exchange()).with("secondStepCartCheckout");
    }

    @Bean
    Binding thirdCartCheckoutBinding(){
        return BindingBuilder.bind(thirdStepCartCheckout()).to(exchange()).with("thirdStepCartCheckout");
    }


}