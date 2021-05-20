package br.com.marcelbraghini.adapters.resources;

import br.com.marcelbraghini.adapters.resources.domain.Coin;
import br.com.marcelbraghini.adapters.resources.domain.CoinAcronym;
import br.com.marcelbraghini.adapters.resources.domain.Type;
import br.com.marcelbraghini.adapters.resources.domain.Wallet;
import br.com.marcelbraghini.infrastructure.repository.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Path("/v1/wallet")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WalletResource {

    private final Logger log = LoggerFactory.getLogger(WalletResource.class);

    @Inject
    WalletRepository walletRepository;

    @GET
    public Response getWallets() {
        try {
            final List<Wallet> wallets = generateResponse(walletRepository.findAllWallets());
            return Response.status(Response.Status.OK).entity(wallets).build();
        } catch (final Exception e) {
            log.error(format("[WalletResource:getWallet] Exception %s", e.getMessage()));
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    private List<Wallet> generateResponse(final List<br.com.marcelbraghini.entities.Wallet> wallets) {

        List<Wallet> newWallets = new ArrayList<>();

        wallets.forEach(wallet -> {
            List<Coin> coins = new ArrayList<>();

            wallet.getCoins().forEach(coin -> coins.add(new Coin.Builder()
                    .withCoinAcronym(CoinAcronym.valueOf(coin.getCoinAcronym().toString()))
                    .withPrice(coin.getPrice())
                    .withFraction(coin.getFraction())
                    .withTotalValue(coin.getTotalValue())
                    .build()));

            newWallets.add(new Wallet.Builder()
                    .withType(Type.valueOf(wallet.getType().toString()))
                    .withTotalValue(wallet.getTotalValue())
                    .withCoins(coins)
                    .build());
        });

        return newWallets;
    }
}