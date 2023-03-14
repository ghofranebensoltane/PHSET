package com.pidev.phset.botpressclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Qnas {
    private List<Qna> qnas;

}
