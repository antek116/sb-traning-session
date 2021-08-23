package com.sbbetting.training.security.token;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TokenType {

    BEARER("Bearer");

    private final String name;

    TokenType(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }
}
