package org.redhat.developer;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.smallrye.mutiny.Uni;
import org.redhat.developer.models.Request;
import org.redhat.developer.models.RequestDto;
import org.redhat.developer.models.Result;
import org.redhat.developer.models.ResultDto;

@Path("/test")
public class RestHandler {

    @Inject
    IService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> test(RequestDto request) {

        CompletableFuture<Response> result = service.doSomethingAsync(Request.from(request))
                .thenApply(x -> ResultDto.from(x))
                .thenApply(x -> Response.ok(x).build());

        return Uni.createFrom().completionStage(result);
    }
}
