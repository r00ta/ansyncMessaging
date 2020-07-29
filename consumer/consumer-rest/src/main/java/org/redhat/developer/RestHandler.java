package org.redhat.developer;

import java.awt.PageAttributes;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.redhat.developer.models.Request;
import org.redhat.developer.models.RequestDto;

@Path("/test")
public class RestHandler {
    @Inject
    IService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response test(RequestDto request){
        service.doSomethingAsync(Request.from(request));
        return Response.ok().build();
    }

}
