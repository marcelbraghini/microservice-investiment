package br.com.marcelbraghini;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@ApplicationScoped
public class AppLifecycleBean {

    private final Logger log = LoggerFactory.getLogger(AppLifecycleBean.class);

    void onStart(@Observes StartupEvent event) {
        log.info("The application is starting...");
    }

    void onStop(@Observes ShutdownEvent event) {
        log.info("The application is stopping...");
    }

}