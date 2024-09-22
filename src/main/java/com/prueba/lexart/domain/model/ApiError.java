package com.prueba.lexart.domain.model;

public class ApiError {
    String message;
    String causeMessage;

    public ApiError(String message, String causeMessage) {
        this.message = message;
        this.causeMessage = causeMessage;
    }

    public ApiError() {}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCauseMessage() {
        return causeMessage;
    }

    public void setCauseMessage(String causeMessage) {
        this.causeMessage = causeMessage;
    }
}
