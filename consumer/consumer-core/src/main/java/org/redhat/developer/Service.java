package org.redhat.developer;

import java.util.concurrent.CompletableFuture;

import javax.enterprise.context.ApplicationScoped;

import org.redhat.developer.models.Request;
import org.redhat.developer.models.Result;

@ApplicationScoped
public class Service implements IService {

    @Override
    public CompletableFuture<Result> doSomethingAsync(Request request) {
        System.out.println("do something");
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("async");
            return new Result();
        });
    }
}
