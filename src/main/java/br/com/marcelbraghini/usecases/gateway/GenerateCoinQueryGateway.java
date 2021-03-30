package br.com.marcelbraghini.usecases.gateway;

import br.com.marcelbraghini.entities.Type;

import java.util.List;

public interface GenerateCoinQueryGateway {

    void generateCoinQuery(final Type type);

    List<Type> findAllType();
}
