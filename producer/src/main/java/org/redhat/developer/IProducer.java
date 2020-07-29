package org.redhat.developer;

import org.redhat.developer.models.RequestDto;

public interface IProducer {
    void produce(RequestDto request);

}
