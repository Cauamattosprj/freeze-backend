package com.cauamattosprj.freeze.config;

import com.cauamattosprj.freeze.modules.auth.AuthCookieService;
import com.cauamattosprj.freeze.modules.auth.JwtTokenService;
import com.cauamattosprj.freeze.modules.auth.TokenType;
import com.cauamattosprj.freeze.modules.users.User;
import com.cauamattosprj.freeze.modules.users.UserDetailsImpl;
import com.cauamattosprj.freeze.modules.users.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ServletRequestPathUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthCookieService authCookieService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!isPublicUrl(request)) {
            String token = recoveryJwtToken(request);
            if (token != null && !token.isBlank()) {
                try {
                    String subject = jwtTokenService.getSubjectFromToken(token, TokenType.ACCESS);
                    User user = userRepository.findById(UUID.fromString(subject)).orElse(null);
                    if (user != null) {
                        UserDetailsImpl userDetails = new UserDetailsImpl(user);
                        Authentication authentication =
                                new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                } catch (RuntimeException exception) {
                    SecurityContextHolder.clearContext();
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private String recoveryJwtToken(HttpServletRequest request) {
        String cookieToken = authCookieService.getCookieValue(request, AuthCookieService.ACCESS_TOKEN_COOKIE);
        if (cookieToken != null && !cookieToken.isBlank()) {
            return cookieToken;
        }

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    private boolean isPublicUrl(HttpServletRequest request) {
        String requestURI = ServletRequestPathUtils.parse(request).pathWithinApplication().value();
        return Arrays.asList(SecurityConfiguration.ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).contains(requestURI);
    }
}
