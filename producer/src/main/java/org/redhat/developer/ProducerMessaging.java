package org.redhat.developer;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.enterprise.context.ApplicationScoped;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.BackpressureStrategy;
import io.reactivex.subjects.PublishSubject;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.reactivestreams.Publisher;
import org.redhat.developer.models.RequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class ProducerMessaging implements IProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerMessaging.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    private final PublishSubject<String> eventSubject = PublishSubject.create();

    public void produce(RequestDto request) {
        LOGGER.info("Send event request");
        String payload = null;
        try {
            payload = mapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("can't serialize the request");
        }

        eventSubject.onNext(payload);
    }

    @Outgoing("trusty-explainability-request")
    public Publisher<String> getEventPublisher() {
        return eventSubject.toFlowable(BackpressureStrategy.BUFFER);
    }

    private static String urlEncode(String input) {
        return URLEncoder.encode(input, StandardCharsets.UTF_8);
    }
}
