package br.com.marcelbraghini.adapters.scheduler;

import br.com.marcelbraghini.entities.Coin;
import br.com.marcelbraghini.entities.Type;
import br.com.marcelbraghini.entities.Wallet;
import br.com.marcelbraghini.entities.exception.CryptoPriceException;
import br.com.marcelbraghini.entities.exception.ObjectMapperException;
import br.com.marcelbraghini.infrastructure.rabbitmq.RabbitMqClient;
import br.com.marcelbraghini.infrastructure.repository.WalletRepository;
import br.com.marcelbraghini.usecases.gateway.GenerateCoinQueryGateway;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import io.quarkus.scheduler.Scheduled;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongBinaryOperator;

import static java.lang.String.format;

@ApplicationScoped
public class Scheduling {

    ObjectMapper objectMapper = new ObjectMapper();

    private final Logger log = LoggerFactory.getLogger(Scheduling.class);

    @Inject
    GenerateCoinQueryGateway generateCoinQueryGateway;

    @Inject
    WalletRepository walletRepository;

    @Inject
    RabbitMqClient rabbitMqClient;

    @ConfigProperty(name = "message.display.exchange")
    String messageDisplayExchange;

    @ConfigProperty(name = "message.display.routing-key")
    String messageDisplayRoutingKey;

    private AtomicLong delay = new AtomicLong();

    @Scheduled(every="{scheduling.generate.coin.query.price}")
    void generateCoinQuery() {
        try {
            final List<Type> allType = generateCoinQueryGateway.findAllType();

            allType.forEach(type -> generateCoinQueryGateway.generateCoinQuery(type));
        } catch (final CryptoPriceException e) {
            log.error(format("[Scheduling:generateCoinQuery] CryptoPriceException %s", e.getMessage()));
        } catch (final Exception e) {
            log.error(format("[Scheduling:generateCoinQuery] Exception %s", e.getMessage()));
        }
    }

    @Scheduled(every="{scheduling.publish.rabbitmq.message.display}")
    void publishRabbitMqMessageDisplay() {
        try {
            final List<Wallet> allWallets = walletRepository.findAllWallets();
            delay.getAndSet(0);

            allWallets.forEach(wallet -> wallet.getCoins().forEach(coin -> {
                final String body = serializer(coin);

                AMQP.BasicProperties properties = fillBasicProperties();

                rabbitMqClient.publishOnExchange(body, messageDisplayExchange, messageDisplayRoutingKey, properties);
                log.info(format("[Scheduling:publishRabbitMqMessageDisplay] Message published: %s", body));
            }));
        } catch (final ObjectMapperException e) {
            log.error(format("[Scheduling:publishRabbitMqMessageDisplay] ObjectMapperException %s", e.getMessage()));
        } catch (final Exception e) {
            log.error(format("[Scheduling:publishRabbitMqMessageDisplay] Exception %s", e.getMessage()));
        }
    }

    private AMQP.BasicProperties fillBasicProperties() {
        AMQP.BasicProperties.Builder properties = new AMQP.BasicProperties.Builder();
        properties.expiration("3000");
        return properties.build();
    }

    private String serializer(final Coin coin) {
        try {
            return objectMapper.writeValueAsString(coin);
        } catch (final Exception e) {
            log.error(format("[Scheduling:serializer] Exception %s", e.getMessage()));
            throw new ObjectMapperException(e.getMessage(), e);
        }
    }
}
