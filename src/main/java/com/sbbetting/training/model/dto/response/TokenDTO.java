package com.sbbetting.training.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sbbetting.training.security.token.TokenType;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenDTO {

    @JsonProperty("token")
    private final String token;

    @JsonProperty("type")
    private final TokenType type;

    @JsonProperty("refreshToken")
    private final String refreshToken;

    public TokenDTO(String token,
                    TokenType type,
                    String refreshToken) {
        this.token = token;
        this.type = type;
        this.refreshToken = refreshToken;
    }

    public String getToken() {
        return token;
    }

    public TokenType getType() {
        return type;
    }
}
