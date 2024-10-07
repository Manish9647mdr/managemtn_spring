package com.tkk.projectmgtsystem.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
