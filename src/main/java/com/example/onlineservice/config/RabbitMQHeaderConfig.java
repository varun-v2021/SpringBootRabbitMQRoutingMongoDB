package com.example.onlineservice.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQHeaderConfig {

	@Bean
	Queue marketingQueue() {
		return new Queue("marketingQueue", false);
	}

	@Bean
	Queue financeQueue() {
		return new Queue("financeQueue", false);
	}

	@Bean
	Queue adminQueue() {
		return new Queue("adminQueue", false);
	}

	@Bean
	HeadersExchange headerExchange() {
		return new HeadersExchange("header-exchange");
	}

	@Bean
	Binding marketingBinding(Queue marketingQueue, HeadersExchange headerExchange) {
		return BindingBuilder.bind(marketingQueue).to(headerExchange).where("department").matches("marketing");
	}

	@Bean
	Binding financeBinding(Queue financeQueue, HeadersExchange headerExchange) {
		return BindingBuilder.bind(financeQueue).to(headerExchange).where("department").matches("finance");
	}

	@Bean
	Binding adminBinding(Queue adminQueue, HeadersExchange headerExchange) {
		return BindingBuilder.bind(adminQueue).to(headerExchange).where("department").matches("admin");
	}

}
