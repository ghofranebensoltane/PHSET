package com.pidev.phset.botpressclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Data {
    @JsonProperty
    private String action;
    @JsonProperty
    private List<String> contexts;
    @JsonProperty
    private boolean enabled;
    private Answers answers;
    private Questions questions;
    @JsonProperty
    private String redirectFlow;
    @JsonProperty
    private String redirectNode;

}