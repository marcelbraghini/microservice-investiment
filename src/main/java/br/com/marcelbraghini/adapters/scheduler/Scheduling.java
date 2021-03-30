package br.com.marcelbraghini.adapters.scheduler;

import br.com.marcelbraghini.entities.Type;
import br.com.marcelbraghini.usecases.gateway.GenerateCoinQueryGateway;
import io.quarkus.scheduler.Scheduled;
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

    @Scheduled(every="{scheduling.generate.coin.query.price}")
    void generateCoinQuery() {
        try {
            final List<Type> allType = generateCoinQueryGateway.findAllType();

            allType.forEach(type -> {
                generateCoinQueryGateway.generateCoinQuery(type);
            });
        } catch (final Exception e) {
            log.error(format("[Scheduling:generateCoinQuery] Exception %s", e.getMessage()));
        }
    }
}
