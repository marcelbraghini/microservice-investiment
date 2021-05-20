package br.com.marcelbraghini.infrastructure.repository;

import br.com.marcelbraghini.entities.MessageDisplay;
import io.quarkus.mongodb.panache.PanacheMongoRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MessageDisplayRepository implements PanacheMongoRepository<MessageDisplay> {

    public MessageDisplay findByNotDelivered(){
        return find("delivered", false).firstResult();
    }
}
