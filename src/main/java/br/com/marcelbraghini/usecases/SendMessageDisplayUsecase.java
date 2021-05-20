package br.com.marcelbraghini.usecases;

import br.com.marcelbraghini.entities.MessageDisplay;
import br.com.marcelbraghini.entities.exception.SendMessageDisplayException;
import br.com.marcelbraghini.infrastructure.nodemcudisplay.SendMessageDisplay;
import br.com.marcelbraghini.infrastructure.repository.MessageDisplayRepository;
import br.com.marcelbraghini.usecases.gateway.SendMessageDisplayGateway;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class SendMessageDisplayUsecase implements SendMessageDisplayGateway {

    @Inject
    @RestClient
    SendMessageDisplay sendMessageDisplay;

    @Inject
    MessageDisplayRepository messageDisplayRepository;

    @ConfigProperty(name = "scheduling.send.message.display.key")
    String key;

    @Override
    public void sendMessageDisplay() {
        try {
            final MessageDisplay messageDisplay = messageDisplayRepository.findByNotDelivered();

            if (messageDisplay != null) {

                br.com.marcelbraghini.infrastructure.nodemcudisplay.domain.MessageDisplay message =
                        new br.com.marcelbraghini.infrastructure.nodemcudisplay.domain.MessageDisplay(
                                messageDisplay.getHeaderText(), messageDisplay.getBodyText(), key
                        );

                sendMessageDisplay.sendMessage(message);

                removeDeliveryExecute(messageDisplay);
            }
        } catch (final Exception e) {
            throw new SendMessageDisplayException(e.getMessage(), e);
        }
    }

    private void removeDeliveryExecute(final MessageDisplay messageDisplay) {
        messageDisplayRepository.delete(messageDisplay);
    }
}
