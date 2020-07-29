package org.redhat.developer.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestDto {
    @JsonProperty("executionId")
    public String executionId;
}
