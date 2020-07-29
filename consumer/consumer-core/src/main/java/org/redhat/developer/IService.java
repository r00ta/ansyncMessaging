package org.redhat.developer;

import java.util.concurrent.CompletableFuture;

import org.redhat.developer.models.Request;
import org.redhat.developer.models.Result;

public interface IService {

    CompletableFuture<Result> doSomethingAsync(Request request);
}
