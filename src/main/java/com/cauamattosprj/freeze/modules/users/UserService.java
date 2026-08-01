package com.cauamattosprj.freeze.modules.users;

import com.cauamattosprj.freeze.modules.users.dtos.UserCreateDTO;
import com.cauamattosprj.freeze.modules.users.dtos.UserLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public UserDetailsImpl authenticateUser(UserLoginDTO userLoginDTO) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(), userLoginDTO.getPassword());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        return (UserDetailsImpl) authentication.getPrincipal();
    }

    public void createUser(UserCreateDTO userCreateDTO) {
        String rawPassword = userCreateDTO.getPassword();
        User newUser = new User(userCreateDTO);
        newUser.setPassword(passwordEncoder.encode(rawPassword));

        userRepository.save(newUser);
    }
}
