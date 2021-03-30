package br.com.marcelbraghini.infrastructure.repository;

import br.com.marcelbraghini.entities.Type;
import br.com.marcelbraghini.entities.Wallet;
import io.quarkus.mongodb.panache.PanacheMongoRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class WalletRepository implements PanacheMongoRepository<Wallet> {

    public Wallet findByType(final Type type){
        return find("type", type).firstResult();
    }

    public List<Wallet> findAllWallets() {
        return findAll().list();
    }
}
