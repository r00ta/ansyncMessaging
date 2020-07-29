package org.redhat.developer;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import io.quarkus.runtime.Startup;
import io.quarkus.scheduler.Scheduled;
import org.redhat.developer.models.RequestDto;

@Singleton
public class GeneratorLoop {

    @Inject
    IProducer producer;

    @Scheduled(every="20s")
    void increment() {
        producer.produce(new RequestDto());
    }
}
