package com.pidev.phset.configures;


import com.stripe.param.ChargeCreateParams;

public class ChargeCreateParamsBuilder {

    private ChargeCreateParams params;

    public ChargeCreateParamsBuilder() {
        params = ChargeCreateParams.builder().build();
    }

    public ChargeCreateParamsBuilder setAmount(Long amount) {
        ChargeCreateParams.builder().setAmount(amount);
        return this;
    }

    public ChargeCreateParamsBuilder setCurrency(String currency) {
        ChargeCreateParams.builder().setCurrency(currency);
        return this;
    }

    public ChargeCreateParamsBuilder setSource(String source) {
        ChargeCreateParams.builder().setSource(source);
        return this;
    }

    public ChargeCreateParams build() {
        return params;
    }
}