package br.com.marcelbraghini.adapters.resources;

import br.com.marcelbraghini.adapters.resources.domain.Type;
import br.com.marcelbraghini.adapters.resources.domain.WalletBase;
import br.com.marcelbraghini.entities.CoinAcronym;
import br.com.marcelbraghini.infrastructure.repository.WalletBaseRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Path("/v1/wallet-base")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WalletBaseResource {

    private final Logger log = LoggerFactory.getLogger(WalletBaseResource.class);

    @Inject
    WalletBaseRepository walletBaseRepository;

    @GET
    public Response getWalletBases() {
        try {
            final List<WalletBase> walletBases = convertWalletBaseToResponse(walletBaseRepository.findAllWalletsBase());
            return Response.status(Response.Status.OK).entity(walletBases).build();
        } catch (final Exception e) {
            log.error(format("[WalletResource:getWalletBase] Exception %s", e.getMessage()));
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    public Response saveWalletBase(final WalletBase walletBase) {
        try {
            walletBaseRepository.persist(convertWalletBaseToSave(walletBase));
            return Response.status(Response.Status.CREATED).entity(walletBase).build();
        } catch (final Exception e) {
            log.error(format("[WalletResource:saveWalletBase] Exception %s", e.getMessage()));
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("{id}")
    public Response updateWalletBase(@PathParam("id") String id, final WalletBase walletBase) {
        try {
            br.com.marcelbraghini.entities.WalletBase walletSaved = walletBaseRepository.findById(new ObjectId(id));

            walletSaved.setType(br.com.marcelbraghini.entities.Type.valueOf(walletBase.getType().toString()));
            walletSaved.setQuantity(walletBase.getQuantity());
            walletSaved.setCoinAcronym(CoinAcronym.valueOf(walletBase.getCoinAcronym().toString()));

            walletBaseRepository.update(walletSaved);
            return Response.status(Response.Status.OK).entity(walletBase).build();
        } catch (final Exception e) {
            log.error(format("[WalletResource:updateWalletBase] Exception %s", e.getMessage()));

            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    private List<WalletBase> convertWalletBaseToResponse(final List<br.com.marcelbraghini.entities.WalletBase> walletBases) {
        List<WalletBase> newWallets = new ArrayList<>();

        walletBases.forEach(walletBase -> newWallets.add(new WalletBase.Builder()
                .withType(Type.valueOf(walletBase.getType().toString()))
                .withCoinAcronym(br.com.marcelbraghini.adapters.resources.domain.CoinAcronym.valueOf(walletBase.getCoinAcronym().toString()))
                .withQuantity(walletBase.getQuantity())
                .build()));

        return newWallets;
    }

    private br.com.marcelbraghini.entities.WalletBase convertWalletBaseToSave(final WalletBase walletBase) {
        br.com.marcelbraghini.entities.WalletBase newWalletBase = new br.com.marcelbraghini.entities.WalletBase();

        newWalletBase.setType(br.com.marcelbraghini.entities.Type.valueOf(walletBase.getType().toString()));
        newWalletBase.setCoinAcronym(CoinAcronym.valueOf(walletBase.getCoinAcronym().toString()));
        newWalletBase.setQuantity(walletBase.getQuantity());

        return newWalletBase;
    }
}