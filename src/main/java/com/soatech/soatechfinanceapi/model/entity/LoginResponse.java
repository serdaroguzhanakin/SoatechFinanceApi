package com.soatech.soatechfinanceapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String status;

    public String getStatus() {
        return status;
    }

    public String getToken() {
        return this.token;
    }
}