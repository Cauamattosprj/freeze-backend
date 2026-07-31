package com.cauamattosprj.freeze.modules.users;

import com.cauamattosprj.freeze.config.SecurityConfiguration;
import com.cauamattosprj.freeze.modules.auth.JwtTokenService;
import com.cauamattosprj.freeze.modules.users.dtos.UserCreateDTO;
import com.cauamattosprj.freeze.modules.users.dtos.UserLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String authenticateUser(UserLoginDTO userLoginDTO) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(), userLoginDTO.getPassword());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return jwtTokenService.generateToken(userDetails);
    }

    public void createUser(UserCreateDTO userCreateDTO) {
        String rawPassword = userCreateDTO.getPassword();
        User newUser = new User(userCreateDTO);
        newUser.setPassword(passwordEncoder.encode(rawPassword));

        userRepository.save(newUser);
    }
}
