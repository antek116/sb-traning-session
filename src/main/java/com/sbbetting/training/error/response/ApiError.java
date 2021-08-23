package com.sbbetting.training.error.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"code", "status", "message", "timestamp"})
public class ApiError {

    @JsonProperty("code")
    private final int code;

    @JsonProperty("status")
    private final String status;

    @JsonProperty("message")
    private final String message;

    @JsonProperty("timestamp")
    private final long timestamp;

    public ApiError(int code,
                    String status,
                    String message,
                    long timestamp) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
