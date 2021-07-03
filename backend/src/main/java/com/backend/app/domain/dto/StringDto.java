package com.backend.app.domain.dto;

public class StringDto {
    public String message;

    public StringDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
