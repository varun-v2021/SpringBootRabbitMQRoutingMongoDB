package com.example.onlineservice.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.onlineservice.model.Employee;
import com.example.onlineservice.repository.EmployeeRepository;
import com.example.onlineservice.service.RabbitMQSender;


@RestController
@RequestMapping(value = "/onlineservice-rabbitmq/")
public class RabbitMQWebController {

	@Autowired
	RabbitMQSender rabbitMQSender;
	
	//@Autowired
	//EmployeeRepository employeeRepository;

	@GetMapping(value = "/producer")
	public String producer(@RequestParam("empName") String empName,@RequestParam("empId") String empId) {
	
	Employee emp=new Employee();
	emp.setEmpId(empId);
	emp.setEmpName(empName);
	rabbitMQSender.send(emp);

		return "Message sent to the RabbitMQ JavaInUse Successfully";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/api/swagger-sample")
	public String sayHello() {
		return "Swagger Hello World";
	}

	@Autowired
	private AmqpTemplate amqpTemplate;

	@GetMapping(value = "/direct/producer")
	public String producer(@RequestParam("exchangeName") String exchange, @RequestParam("routingKey") String routingKey,
			@RequestParam("messageData") String messageData) {

		amqpTemplate.convertAndSend(exchange, routingKey, messageData);

		return "Message sent to the RabbitMQ Direct Exchange Successfully";
	}
	
	@GetMapping(value = "/fanout/producer")
	public String fProducer(@RequestParam("exchangeName") String exchange,
			@RequestParam("messageData") String messageData) {

		amqpTemplate.convertAndSend(exchange, "", messageData);

		return "Message sent to the RabbitMQ Fanout Exchange Successfully";
	}
	
	@GetMapping(value = "/topic/producer")
	public String tProducer(@RequestParam("exchangeName") String exchange, @RequestParam("routingKey") String routingKey,
			@RequestParam("messageData") String messageData) {

		amqpTemplate.convertAndSend(exchange, routingKey, messageData);

		return "Message sent to the RabbitMQ Topic Exchange Successfully";
	}
	
	@GetMapping(value = "/header/producer")
	public String hProducer(@RequestParam("exchangeName") String exchange, @RequestParam("department") String department,
			@RequestParam("messageData") String messageData) {

		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setHeader("department", department);
		MessageConverter messageConverter = new SimpleMessageConverter();
		Message message = messageConverter.toMessage(messageData, messageProperties);
		amqpTemplate.send(exchange, "", message);

		return "Message sent to the RabbitMQ Header Exchange Successfully";
	}
}
