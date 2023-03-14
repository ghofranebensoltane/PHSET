package com.pidev.phset.botpressclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Questions {
    @JsonProperty
    private List<String> en;
}