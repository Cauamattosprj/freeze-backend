package com.cauamattosprj.freeze.modules.auth;

import lombok.Getter;

@Getter
public class LoginResponseDTO {
    private String accessToken;

    public LoginResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }
}
