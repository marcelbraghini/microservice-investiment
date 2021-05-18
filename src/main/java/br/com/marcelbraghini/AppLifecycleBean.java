package br.com.marcelbraghini;

import br.com.marcelbraghini.infrastructure.rabbitmq.RabbitMqClient;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class AppLifecycleBean {

    private final Logger log = LoggerFactory.getLogger(AppLifecycleBean.class);

    @Inject
    RabbitMqClient rabbitMqClient;

    @ConfigProperty(name = "message.display.exchange")
    String messageDisplayExchange;

    @ConfigProperty(name = "message.display.queue")
    String messageDisplayQueue;

    @ConfigProperty(name = "message.display.routing-key")
    String messageDisplayRoutingKey;

    public void onStart(@Observes StartupEvent event) {
        log.info("The application is starting...");
        rabbitMqClient.startConsumer(messageDisplayExchange, messageDisplayQueue, messageDisplayRoutingKey);
    }

    public void onStop(@Observes ShutdownEvent event) {
        log.info("The application is stopping...");
        rabbitMqClient.closeRabbitMq();
    }

}