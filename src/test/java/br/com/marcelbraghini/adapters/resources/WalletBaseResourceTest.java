package br.com.marcelbraghini.adapters.resources;

import br.com.marcelbraghini.entities.CoinAcronym;
import br.com.marcelbraghini.entities.Type;
import br.com.marcelbraghini.infrastructure.repository.WalletBaseRepository;
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
public class WalletBaseResourceTest {

    @Mock
    private WalletBaseRepository walletBaseRepository;

    @InjectMocks
    private WalletBaseResource walletBaseResource;

    @Test
    public void testRetrieveWallets() {
        List<br.com.marcelbraghini.entities.WalletBase> walletBases = new ArrayList<>();

        walletBases.add(new br.com.marcelbraghini.entities.WalletBase(
                                        Type.CRYPTOCURRENCY,
                                        CoinAcronym.BTC,
                                        new BigDecimal("200.00")));

        when(walletBaseRepository.findAllWalletsBase()).thenReturn(walletBases);

        Response response = walletBaseResource.getWalletBases();

        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    public void testRetrieveWalletThenThrowsException() {
        List<br.com.marcelbraghini.entities.WalletBase> walletBases = new ArrayList<>();

        walletBases.add(new br.com.marcelbraghini.entities.WalletBase(
                Type.CRYPTOCURRENCY,
                CoinAcronym.BTC,
                new BigDecimal("200.00")));

        when(walletBaseRepository.findAllWalletsBase()).thenThrow(new RuntimeException());

        Response response = walletBaseResource.getWalletBases();

        assertNotNull(response);
        assertEquals(400, response.getStatus());
        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }
}