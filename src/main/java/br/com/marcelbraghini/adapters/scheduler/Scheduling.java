package br.com.marcelbraghini.adapters.scheduler;

import br.com.marcelbraghini.entities.Type;
import br.com.marcelbraghini.entities.exception.GetCryptoPriceException;
import br.com.marcelbraghini.entities.exception.SendMessageDisplayException;
import br.com.marcelbraghini.usecases.gateway.GenerateCoinQueryGateway;
import br.com.marcelbraghini.usecases.gateway.SendMessageDisplayGateway;
import io.quarkus.scheduler.Scheduled;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

import static java.lang.String.format;

@ApplicationScoped
public class Scheduling {

    private final Logger log = LoggerFactory.getLogger(Scheduling.class);

    @Inject
    GenerateCoinQueryGateway generateCoinQueryGateway;

    @Inject
    SendMessageDisplayGateway sendMessageDisplayGateway;

    @Scheduled(every="{scheduling.generate.coin.query.price}")
    void generateCoinQuery() {
        try {
            final List<Type> allType = generateCoinQueryGateway.findAllType();
            allType.forEach(type -> generateCoinQueryGateway.generateCoinQuery(type));
        } catch (final GetCryptoPriceException e) {
            log.error(format("[Scheduling:generateCoinQuery] GetCryptoPriceException %s", e.getMessage()));
        } catch (final Exception e) {
            log.error(format("[Scheduling:generateCoinQuery] Exception %s", e.getMessage()));
        }
    }

    @Scheduled(every="{scheduling.send.message.display.time}")
    void sendMessageDisplay() {
        try {
            sendMessageDisplayGateway.sendMessageDisplay();
        } catch (final SendMessageDisplayException e) {
            log.error(format("[Scheduling:sendMessageDisplay] SendMessageDisplayException %s", e.getMessage()));
        } catch (final Exception e) {
            log.error(format("[Scheduling:sendMessageDisplay] Exception %s", e.getMessage()));
        }
    }

    @Scheduled(every="{scheduling.prepare.message.display.time}")
    void prepareMessageDisplay() {
        try {
            generateCoinQueryGateway.prepareDisplayMessage();
        } catch (final Exception e) {
            log.error(format("[Scheduling:prepareMessageDisplay] Exception %s", e.getMessage()));
        }
    }
}
