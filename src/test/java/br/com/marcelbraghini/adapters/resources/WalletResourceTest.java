package br.com.marcelbraghini.adapters.resources;

import br.com.marcelbraghini.entities.Coin;
import br.com.marcelbraghini.entities.CoinAcronym;
import br.com.marcelbraghini.entities.Type;
import br.com.marcelbraghini.entities.Wallet;
import br.com.marcelbraghini.infrastructure.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WalletResourceTest {

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletResource walletResource;

    @Test
    public void testRetrieveWallets() {
        List<Wallet> wallets = new ArrayList<>();
        List<Coin> coins = new ArrayList<>();

        coins.add(new Coin.Builder().withCoinAcronym(CoinAcronym.BTC)
                                    .withPrice(new BigDecimal("200.00"))
                                    .withFraction(new BigDecimal("2.00"))
                                    .withTotalValue(new BigDecimal("400.00"))
                                    .build());

        wallets.add(new Wallet.Builder().withType(Type.CRYPTOCURRENCY)
                                        .withTotalValue(new BigDecimal("200.00"))
                                        .withCoins(coins)
                                        .build());

        when(walletRepository.findAllWallets()).thenReturn(wallets);

        Response response = walletResource.getWallets();

        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    public void testRetrieveWalletThenThrowsException() throws Exception {
        List<Wallet> wallets = new ArrayList<>();
        List<Coin> coins = new ArrayList<>();

        coins.add(new Coin.Builder().withCoinAcronym(CoinAcronym.BTC)
                .withPrice(new BigDecimal("200.00"))
                .withFraction(new BigDecimal("2.00"))
                .withTotalValue(new BigDecimal("400.00"))
                .build());

        wallets.add(new Wallet.Builder().withType(Type.CRYPTOCURRENCY)
                .withTotalValue(new BigDecimal("200.00"))
                .withCoins(coins)
                .build());


        when(walletRepository.findAllWallets()).thenThrow(new RuntimeException());

        Response response = walletResource.getWallets();

        assertNotNull(response);
        assertEquals(400, response.getStatus());
        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }
}