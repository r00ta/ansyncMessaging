package org.redhat.developer;

import java.util.concurrent.CompletableFuture;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.vertx.core.Context;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.context.ThreadContext;
import org.redhat.developer.models.Request;
import org.redhat.developer.models.Result;

@ApplicationScoped
public class Service implements IService {

    @Inject
    ManagedExecutor executor;

    @Override
    public CompletableFuture<Result> doSomethingAsync(Request request) {

        System.out.println("do something");
        return CompletableFuture.supplyAsync(() -> new Result(), executor);
    }
}
