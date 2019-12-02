# SpringBootRabbitMQRoutingMongoDB


http://localhost:8080/onlineservice-rabbitmq/direct/producer?exchangeName=direct-exchange&routingKey=admin&messageData=HelloWorldFromDirectExchange
http://localhost:8080/onlineservice-rabbitmq/fanout/producer?exchangeName=fanout-exchange&messageData=HelloWorldFanoutExchange
http://localhost:8080//onlineservice-rabbitmq/topic/producer?exchangeName=topic-exchange&routingKey=queue.admin&messageData=HelloWorldTopicExchange
http://localhost:8080/onlineservice-rabbitmq/header/producer?exchangeName=header-exchange&department=admin&messageData=HelloWorldHeaderExchange
