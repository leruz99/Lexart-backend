package com.prueba.lexart.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record UserDTO(@JsonProperty(value = "id", index = 0) Long Id,
                      @JsonProperty(value = "name", index = 1) String name,
                      @JsonProperty(value = "email", index = 2) String email,
                      @JsonProperty(value = "password", index = 3) String password,
                      @JsonProperty(value = "token", index = 5) String token,
                      @JsonProperty(value = "created", index = 6) LocalDateTime createdAt,
                      @JsonProperty(value = "modified", index = 7) LocalDateTime updateAt,
                      @JsonProperty(value = "last_login", index = 8) LocalDateTime lastLogin,
                      @JsonProperty(value = "isactive", index = 9) boolean isActive) {
}

