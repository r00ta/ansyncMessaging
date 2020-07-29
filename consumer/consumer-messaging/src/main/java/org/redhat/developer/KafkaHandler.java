package org.redhat.developer;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.BackpressureStrategy;
import io.reactivex.subjects.PublishSubject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.reactivestreams.Publisher;
import org.redhat.developer.models.Request;
import org.redhat.developer.models.RequestDto;
import org.redhat.developer.models.ResultDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class KafkaHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaHandler.class);

    private static final URI URI_PRODUCER = URI.create("kafkaHandler/handle");

    private final PublishSubject<String> eventSubject = PublishSubject.create();

    private final static ObjectMapper mapper = new ObjectMapper();
    @Inject
    IService service;

    // Incoming
    @Incoming("trusty-explainability-request")
    public CompletionStage<Void> handleMessage(Message<String> message) {
        LOGGER.info("consuming request");
        RequestDto dto;

        try {
            dto = mapper.readValue(message.getPayload(), RequestDto.class);
        } catch (JsonProcessingException e) {
            LOGGER.error("error deserializing event", e);
            return message.ack();
        }

        service.doSomethingAsync(Request.from(dto));
        sendEvent(new ResultDto());
        return message.ack();
    }

    // Outgoing
    public void sendEvent(ResultDto result) {
        LOGGER.info("Consumer messaging returns result ");
        String payload = null;
        try {
            payload = mapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            LOGGER.error("Serialization error");
        }

        eventSubject.onNext(payload);
    }

    @Outgoing("trusty-explainability-result")
    public Publisher<String> getEventPublisher() {
        return eventSubject.toFlowable(BackpressureStrategy.BUFFER);
    }
}