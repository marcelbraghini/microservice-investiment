package br.com.marcelbraghini.usecases;

import br.com.marcelbraghini.entities.Coin;
import br.com.marcelbraghini.entities.Type;
import br.com.marcelbraghini.entities.Wallet;
import br.com.marcelbraghini.entities.WalletBase;
import br.com.marcelbraghini.infrastructure.brasilbitcoin.GetCryptoPrice;
import br.com.marcelbraghini.infrastructure.repository.WalletBaseRepository;
import br.com.marcelbraghini.infrastructure.repository.WalletRepository;
import br.com.marcelbraghini.usecases.gateway.GenerateCoinQueryGateway;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@ApplicationScoped
public class GenerateCoinQueryUsecase implements GenerateCoinQueryGateway {

    @Inject
    @RestClient
    GetCryptoPrice getCryptoPrice;

    @Inject
    WalletRepository walletRepository;

    @Inject
    WalletBaseRepository walletBaseRepository;

    @Override
    public void generateCoinQuery(final Type type) {
        List<WalletBase> walletsBase = walletBaseRepository.findAllWalletsBase();

        List<Coin> coins = updateWalletPrices(walletsBase);

        Wallet wallet = walletRepository.findByType(type);

        if (wallet != null) {
            wallet.updateWallet(coins, totalValueSum(coins));

            walletRepository.persistOrUpdate(wallet);
        } else {
            final Wallet newWallet = new Wallet.Builder()
                                                .withType(type)
                                                .withTotalValue(totalValueSum(coins))
                                                .withCoins(coins)
                                                .build();

            walletRepository.persist(newWallet);
        }
    }

    @Override
    public List<Type> findAllType() {
        return walletBaseRepository.getAllTypes();
    }

    private BigDecimal totalValueSum(final List<Coin> coins) {
        final List<BigDecimal> values = coins.parallelStream().map(Coin::getTotalValue).collect(toList());

        return BigDecimal.valueOf(values.stream().mapToDouble(BigDecimal::doubleValue).sum());
    }

    private List<Coin> updateWalletPrices(final List<WalletBase> walletsBase) {
        List<Coin> coins = new ArrayList<>();

        walletsBase.stream().forEach(wallet -> {
            final br.com.marcelbraghini.infrastructure.brasilbitcoin.domain.Coin coin =
                    getCryptoPrice.getBitCoinPrice(wallet.getCoinAcronym());

            final BigDecimal valueTotal = new BigDecimal(coin.getSell()).multiply(wallet.getQuantity())
                    .setScale(2, RoundingMode.HALF_EVEN);

            final Coin newCoin = new Coin.Builder()
                                            .withPrice(new BigDecimal(coin.getSell()))
                                            .withFraction(wallet.getQuantity())
                                            .withCoinAcronym(wallet.getCoinAcronym())
                                            .withTotalValue(valueTotal)
                                            .build();

            coins.add(newCoin);
        });

        return coins;
    }
}
