package br.com.marcelbraghini.infrastructure.brasilbitcoin;

import br.com.marcelbraghini.entities.CoinAcronym;
import br.com.marcelbraghini.infrastructure.brasilbitcoin.domain.Coin;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/API")
@RegisterRestClient
public interface GetCryptoPrice {

    @GET
    @Path("/prices/{coinAcronym}")
    Coin getBitCoinPrice(@PathParam CoinAcronym coinAcronym);

}
