package com.backend.app.domain.dto.admin;

import java.time.LocalDateTime;

public class UserDto {
    private long id;
    private String token;
    private LocalDateTime userCreationTime;

    public UserDto() {
    }

    public UserDto(long id, String token, LocalDateTime userCreationTime) {
        this.id = id;
        this.token = token;
        this.userCreationTime = userCreationTime;
    }

    public long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getUserCreationTime() {
        return userCreationTime;
    }
}
