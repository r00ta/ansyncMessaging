package org.redhat.developer.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultDto {
    @JsonProperty("test")
    public String test;

    public ResultDto(){
        this.test = "testpfdjsfoidjsafijdas";
    }
    
    public static ResultDto from(Result result){
        return new ResultDto();
    }
}
