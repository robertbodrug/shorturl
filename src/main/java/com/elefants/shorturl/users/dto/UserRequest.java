package com.elefants.shorturl.users.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String password;
}