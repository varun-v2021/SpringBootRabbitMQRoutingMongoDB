package com.example.onlineservice.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.onlineservice.service.RabbitMQListener;

@Configuration
public class RabbitMQDirectConfig {

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
	DirectExchange exchange() {
		return new DirectExchange("direct-exchange");
	}

	@Bean
	Binding marketingBinding(Queue marketingQueue, DirectExchange exchange) {
		return BindingBuilder.bind(marketingQueue).to(exchange).with("marketing");
	}

	@Bean
	Binding financeBinding(Queue financeQueue, DirectExchange exchange) {
		return BindingBuilder.bind(financeQueue).to(exchange).with("finance");
	}

	@Bean
	Binding adminBinding(Queue adminQueue, DirectExchange exchange) {
		return BindingBuilder.bind(adminQueue).to(exchange).with("admin");
	}
	
	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	
	@Bean
	public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}

	// Listener configurations
	// create MessageListenerContainer using default connection factory
	@Bean
	MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
		simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
		simpleMessageListenerContainer.setQueues(adminQueue());
		simpleMessageListenerContainer.setMessageListener(new RabbitMQListener());
		return simpleMessageListenerContainer;

	}

}
