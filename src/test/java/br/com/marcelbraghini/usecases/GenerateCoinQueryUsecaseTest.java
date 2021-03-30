package br.com.marcelbraghini.usecases;

import br.com.marcelbraghini.entities.*;
import br.com.marcelbraghini.infrastructure.brasilbitcoin.GetCryptoPrice;
import br.com.marcelbraghini.infrastructure.repository.WalletBaseRepository;
import br.com.marcelbraghini.infrastructure.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GenerateCoinQueryUsecaseTest {

    @Mock
    private GetCryptoPrice getCryptoPrice;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private WalletBaseRepository walletBaseRepository;

    @InjectMocks
    private GenerateCoinQueryUsecase generateCoinQueryUsecase;

    @Test
    public void testGenerateCoinQueryWithWalletIsNotNull() {
        List<WalletBase> walletBases = new ArrayList<>();
        List<Coin> coins = new ArrayList<>();

        walletBases.add(new WalletBase(Type.CRYPTOCURRENCY, CoinAcronym.BTC, new BigDecimal("0.2323")));

        coins.add(new Coin.Builder().withCoinAcronym(CoinAcronym.BTC)
                                    .withPrice(new BigDecimal("200.00"))
                                    .withFraction(new BigDecimal("2.00"))
                                    .withTotalValue(new BigDecimal("400.00"))
                                    .build());

        final Wallet wallet = new Wallet.Builder().withType(Type.CRYPTOCURRENCY)
                                                    .withTotalValue(new BigDecimal("200.00"))
                                                    .withCoins(coins)
                                                    .build();

        br.com.marcelbraghini.infrastructure.brasilbitcoin.domain.Coin coin = new br.com.marcelbraghini.infrastructure.brasilbitcoin.domain.Coin();
        coin.setSell("300.00");

        when(walletBaseRepository.findAllWalletsBase()).thenReturn(walletBases);
        when(getCryptoPrice.getBitCoinPrice(CoinAcronym.BTC)).thenReturn(coin);
        when(walletRepository.findByType(Type.CRYPTOCURRENCY)).thenReturn(wallet);

        generateCoinQueryUsecase.generateCoinQuery(Type.CRYPTOCURRENCY);

        verify(walletBaseRepository, times(1)).findAllWalletsBase();

        ArgumentCaptor<Type> captor = ArgumentCaptor.forClass(Type.class);
        verify(walletRepository).findByType(captor.capture());
        assertEquals(captor.getValue(), Type.CRYPTOCURRENCY);

        ArgumentCaptor<Wallet> captorPersist = ArgumentCaptor.forClass(Wallet.class);
        verify(walletRepository).persistOrUpdate(captorPersist.capture());
        assertEquals(captorPersist.getValue().getType(), Type.CRYPTOCURRENCY);
    }

    @Test
    public void testGenerateCoinQueryWithWalletIsNull() {
        List<WalletBase> walletBases = new ArrayList<>();
        walletBases.add(new WalletBase(Type.CRYPTOCURRENCY, CoinAcronym.BTC, new BigDecimal("0.2323")));

        final Wallet wallet = null;

        br.com.marcelbraghini.infrastructure.brasilbitcoin.domain.Coin coin = new br.com.marcelbraghini.infrastructure.brasilbitcoin.domain.Coin();
        coin.setSell("300.00");

        when(walletBaseRepository.findAllWalletsBase()).thenReturn(walletBases);
        when(getCryptoPrice.getBitCoinPrice(CoinAcronym.BTC)).thenReturn(coin);
        when(walletRepository.findByType(Type.CRYPTOCURRENCY)).thenReturn(wallet);

        generateCoinQueryUsecase.generateCoinQuery(Type.CRYPTOCURRENCY);

        ArgumentCaptor<Type> captor = ArgumentCaptor.forClass(Type.class);
        verify(walletRepository).findByType(captor.capture());
        assertEquals(captor.getValue(), Type.CRYPTOCURRENCY);

        verify(walletBaseRepository, times(1)).findAllWalletsBase();

        ArgumentCaptor<Wallet> captorPersist = ArgumentCaptor.forClass(Wallet.class);
        verify(walletRepository).persist(captorPersist.capture());
        assertEquals(captorPersist.getValue().getType(), Type.CRYPTOCURRENCY);
    }

    @Test
    public void testRetrieveWalletThenThrowsException() {
        when(walletBaseRepository.findAllWalletsBase()).thenThrow(new RuntimeException());

        assertThrows(Exception.class, () -> generateCoinQueryUsecase.generateCoinQuery(Type.CRYPTOCURRENCY));
    }
}