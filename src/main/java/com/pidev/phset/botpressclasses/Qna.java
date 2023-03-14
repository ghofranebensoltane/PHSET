package com.pidev.phset.botpressclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Qna {
    @JsonProperty
    private String id;
    private Data data;
}
