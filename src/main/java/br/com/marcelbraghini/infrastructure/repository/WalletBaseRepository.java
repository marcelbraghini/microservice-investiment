package br.com.marcelbraghini.infrastructure.repository;

import br.com.marcelbraghini.entities.Type;
import br.com.marcelbraghini.entities.WalletBase;
import io.quarkus.mongodb.panache.PanacheMongoRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class WalletBaseRepository implements PanacheMongoRepository<WalletBase> {

    public List<WalletBase> findAllWalletsBase() {
        return findAll().list();
    }

    public List<Type> getAllTypes() {
        return findAll().list().stream().map(WalletBase::getType).collect(Collectors.toList());
    }

}
