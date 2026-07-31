package com.cauamattosprj.freeze.modules.users.dtos;

import lombok.Getter;

@Getter
public class UserCreateDTO {
    private String email;
    private String fullName;
    private String password;
}
