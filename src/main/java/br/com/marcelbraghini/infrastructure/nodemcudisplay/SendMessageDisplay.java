package br.com.marcelbraghini.infrastructure.nodemcudisplay;

import br.com.marcelbraghini.infrastructure.nodemcudisplay.domain.MessageDisplay;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RegisterRestClient
public interface SendMessageDisplay {

    @POST
    @Path("/message")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    MessageDisplay sendMessage(final MessageDisplay messageDisplay);

}
