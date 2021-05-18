package br.com.marcelbraghini.infrastructure.rabbitmq;

import com.rabbitmq.client.*;
import io.quarkiverse.rabbitmqclient.RabbitMQClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

import static java.lang.String.format;

@ApplicationScoped
public class RabbitMqClient {

    private static final Logger log = LoggerFactory.getLogger(RabbitMqClient.class);

    @Inject
    RabbitMQClient rabbitMQClient;

    Connection connection;

    Channel channel;

    public void startConsumer(final String exchange, final String queue, final String routingKey) {
        try {
            connection = rabbitMQClient.connect();
            channel = connection.createChannel();
            channel.exchangeDeclare(exchange, BuiltinExchangeType.TOPIC, true);
            channel.queueDeclare(queue, true, false, false, null);
            channel.queueBind(queue, exchange, routingKey);
            channel.basicQos(100);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void onReceive() {
        try {
            channel.basicConsume("message.display.queue", true, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    log.info("Received: " + new String(body, StandardCharsets.UTF_8));
                }
            });
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void publishOnExchange(final String message, final String messageDisplayExchange,
                                  final String messageDisplayRoutingKey, final AMQP.BasicProperties properties) {
        try {
            channel.basicPublish(messageDisplayExchange, messageDisplayRoutingKey, properties, message.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void closeRabbitMq() {
        try {
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            log.error(format("[RabbitMqClient:closeRabbitMq] Exception %s", e.getMessage()));
        }
    }
}
