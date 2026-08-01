package com.cauamattosprj.freeze.modules.users;

import com.cauamattosprj.freeze.modules.auth.LoginResponseDTO;
import com.cauamattosprj.freeze.modules.users.dtos.UserCreateDTO;
import com.cauamattosprj.freeze.modules.users.dtos.UserLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> authenticateUser(@RequestBody UserLoginDTO loginUserDto) {
        System.out.println("Requisição de login recebida");
        String token = userService.authenticateUser(loginUserDto);
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/signUp")
    public ResponseEntity<Void> createUser(@RequestBody UserCreateDTO userCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable UUID userId) {
        return new ResponseEntity<User>(userService.getUserById(userId), HttpStatus.OK);
    }
}