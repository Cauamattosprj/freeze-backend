package com.cauamattosprj.freeze.modules.users.dtos;

import com.cauamattosprj.freeze.modules.users.User;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class UserDTO {
    private UUID id;
    private String fullName;
    private String email;
    private LocalDate birthDate;
    private LocalDateTime createdAt;

    public UserDTO(User user) {
        this.id = user.getId();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.birthDate = user.getBirthDate();
        this.createdAt = user.getCreatedAt();
    }
}
