package com.cauamattosprj.freeze.modules.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class AuthCookieService {
    public static final String ACCESS_TOKEN_COOKIE = "access_token";

    public static final String REFRESH_TOKEN_COOKIE = "refresh_token";

    private static final Duration ACCESS_TOKEN_MAX_AGE = Duration.ofMinutes(15);

    private static final Duration REFRESH_TOKEN_MAX_AGE = Duration.ofDays(7);

    @Value("${app.cookie.secure:true}")
    private boolean secure;

    @Value("${app.cookie.same-site:None}")
    private String sameSite;

    @Value("${app.cookie.path:/}")
    private String cookiePath;

    public ResponseCookie createAccessTokenCookie(String accessToken) {
        return createCookie(ACCESS_TOKEN_COOKIE, accessToken, ACCESS_TOKEN_MAX_AGE);
    }

    public ResponseCookie createRefreshTokenCookie(String refreshToken) {
        return createCookie(REFRESH_TOKEN_COOKIE, refreshToken, REFRESH_TOKEN_MAX_AGE);
    }

    public ResponseCookie createExpiredAccessTokenCookie() {
        return createCookie(ACCESS_TOKEN_COOKIE, "", Duration.ZERO);
    }

    public ResponseCookie createExpiredRefreshTokenCookie() {
        return createCookie(REFRESH_TOKEN_COOKIE, "", Duration.ZERO);
    }

    public String getCookieValue(HttpServletRequest request, String name) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (name.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private ResponseCookie createCookie(String name, String value, Duration maxAge) {
        return ResponseCookie.from(name, value)
                .httpOnly(true)
                .secure(secure)
                .sameSite(sameSite)
                .path(cookiePath)
                .maxAge(maxAge)
                .build();
    }
}
