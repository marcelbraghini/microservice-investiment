package br.com.marcelbraghini.usecases;

import br.com.marcelbraghini.entities.*;
import br.com.marcelbraghini.entities.exception.GetCryptoPriceException;
import br.com.marcelbraghini.infrastructure.brasilbitcoin.GetCryptoPrice;
import br.com.marcelbraghini.infrastructure.repository.MessageDisplayRepository;
import br.com.marcelbraghini.infrastructure.repository.WalletBaseRepository;
import br.com.marcelbraghini.infrastructure.repository.WalletRepository;
import br.com.marcelbraghini.usecases.gateway.GenerateCoinQueryGateway;
import org.eclipse.microprofile.config.inject.ConfigProperty;
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

    @Inject
    MessageDisplayRepository messageDisplayRepository;

    @ConfigProperty(name = "scheduling.send.message.display.key")
    String key;

    @Override
    public void generateCoinQuery(final Type type) {

        List<WalletBase> walletsBase = walletBaseRepository.findAllWalletsBase();

        List<Coin> coins = updateWalletPrices(walletsBase);

        Wallet wallet = walletRepository.findByType(type);

        if (wallet != null) {
            wallet.updateWallet(coins, totalValueSum(coins));
            walletRepository.persistOrUpdate(wallet);
        } else {
            final Wallet newWallet = convertWallet(type, coins);
            walletRepository.persist(newWallet);
        }
    }

    @Override
    public void prepareDisplayMessage() {
        
        /* Generate total from wallet */
        final Wallet wallet = walletRepository.findAllWallets().get(0);
        saveMessageDisplayCoin("My", " wallet total:", wallet.getTotalValue().toString());

        /* Generate coin value from wallet */
        wallet.getCoins().forEach(w -> {
            saveMessageDisplayCoin(w.getCoinAcronym().toString(), " wallet: ", w.getTotalValue().toString());

            /* Get coin price from market */
            final br.com.marcelbraghini.infrastructure.brasilbitcoin.domain.Coin coin =
                    getCryptoPrice.getCryptoPrice(w.getCoinAcronym());

            saveMessageDisplayCoin(w.getCoinAcronym().toString(), " of market: ", coin.getSell());
        });
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

        try {
            walletsBase.forEach(wallet -> {
                final br.com.marcelbraghini.infrastructure.brasilbitcoin.domain.Coin coin =
                        getCryptoPrice.getCryptoPrice(wallet.getCoinAcronym());

                final BigDecimal valueTotal = new BigDecimal(coin.getSell()).multiply(wallet.getQuantity())
                        .setScale(2, RoundingMode.HALF_EVEN);

                final Coin newCoin = convertWalletBase(wallet, coin, valueTotal);

                coins.add(newCoin);
            });

        return coins;

        } catch (final Exception e) {
            throw new GetCryptoPriceException(e.getMessage(), e);
        }
    }

    private void saveMessageDisplayCoin(final String coin, final String text, final String value) {
        final MessageDisplay messageDisplay = new MessageDisplay(
                coin+text,
                "R$ "+value,
                key
        );
        messageDisplayRepository.persist(messageDisplay);
    }

    private Wallet convertWallet(Type type, List<Coin> coins) {
        return new Wallet.Builder()
                .withType(type)
                .withTotalValue(totalValueSum(coins))
                .withCoins(coins)
                .build();
    }

    private Coin convertWalletBase(final WalletBase wallet,
                                   final br.com.marcelbraghini.infrastructure.brasilbitcoin.domain.Coin coin,
                                   final BigDecimal valueTotal) {
        return new Coin.Builder()
                        .withPrice(new BigDecimal(coin.getSell()))
                        .withFraction(wallet.getQuantity())
                        .withCoinAcronym(wallet.getCoinAcronym())
                        .withTotalValue(valueTotal)
                        .build();
    }
}
