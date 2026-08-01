package com.cauamattosprj.freeze.modules.users;

import com.cauamattosprj.freeze.modules.auth.AuthCookieService;
import com.cauamattosprj.freeze.modules.auth.JwtTokenService;
import com.cauamattosprj.freeze.modules.users.dtos.UserCreateDTO;
import com.cauamattosprj.freeze.modules.users.dtos.UserLoginDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private AuthCookieService authCookieService;

    @PostMapping("/login")
    public ResponseEntity<Void> authenticateUser(@RequestBody UserLoginDTO loginUserDto, HttpServletResponse response) {
        UserDetailsImpl userDetails = userService.authenticateUser(loginUserDto);

        String accessToken = jwtTokenService.generateToken(userDetails);
        String refreshToken = jwtTokenService.generateRefreshToken(userDetails);

        response.addHeader("Set-Cookie", authCookieService.createAccessTokenCookie(accessToken).toString());
        response.addHeader("Set-Cookie", authCookieService.createRefreshTokenCookie(refreshToken).toString());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/signUp")
    public ResponseEntity<Void> createUser(@RequestBody UserCreateDTO userCreateDTO) {
        userService.createUser(userCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable UUID userId) {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }
}
