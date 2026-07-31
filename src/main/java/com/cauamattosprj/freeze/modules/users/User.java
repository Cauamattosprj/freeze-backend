package com.cauamattosprj.freeze.modules.users;

import com.cauamattosprj.freeze.modules.users.dtos.UserCreateDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "full_name")
    private String fullName;
    @Column(unique = true)
    private String email;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    private String password;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public User(UserCreateDTO userCreateDTO) {
        this.email = userCreateDTO.getEmail();
        this.password = userCreateDTO.getPassword();
        this.fullName = userCreateDTO.getFullName();
    }
}
