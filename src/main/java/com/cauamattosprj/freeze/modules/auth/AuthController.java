package com.cauamattosprj.freeze.modules.auth;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.cauamattosprj.freeze.modules.users.User;
import com.cauamattosprj.freeze.modules.users.UserDetailsImpl;
import com.cauamattosprj.freeze.modules.users.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private AuthCookieService authCookieService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/refresh")
    public ResponseEntity<Void> refresh(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = authCookieService.getCookieValue(request, AuthCookieService.REFRESH_TOKEN_COOKIE);
        if (refreshToken == null || refreshToken.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String subject;
        try {
            subject = jwtTokenService.getSubjectFromToken(refreshToken, TokenType.REFRESH);
        } catch (JWTVerificationException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userRepository.findById(UUID.fromString(subject)).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String newAccessToken = jwtTokenService.generateToken(new UserDetailsImpl(user));
        response.addHeader("Set-Cookie", authCookieService.createAccessTokenCookie(newAccessToken).toString());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/validate")
    public ResponseEntity<Void> validate(HttpServletRequest request) {
        String accessToken = authCookieService.getCookieValue(request, AuthCookieService.ACCESS_TOKEN_COOKIE);
        boolean valid = accessToken != null && jwtTokenService.isTokenValid(accessToken, TokenType.ACCESS);
        return valid
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        response.addHeader("Set-Cookie", authCookieService.createExpiredAccessTokenCookie().toString());
        response.addHeader("Set-Cookie", authCookieService.createExpiredRefreshTokenCookie().toString());
        return ResponseEntity.ok().build();
    }
}
