package com.cauamattosprj.freeze.modules.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cauamattosprj.freeze.modules.users.UserDetailsImpl;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class JwtTokenService {
    private static final String SECRET_KEY = "freeze-secret";

    private static final String ISSUER = "freeze-backend";

    private static final String CLAIM_TYPE = "type";

    private static final Duration ACCESS_TOKEN_DURATION = Duration.ofMinutes(15);

    private static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(7);

    public String generateToken(UserDetailsImpl userDetails) {
        return generateToken(userDetails, TokenType.ACCESS);
    }

    public String generateRefreshToken(UserDetailsImpl userDetails) {
        return generateToken(userDetails, TokenType.REFRESH);
    }

    public String generateToken(UserDetailsImpl userDetails, TokenType tokenType) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withIssuedAt(Instant.now())
                    .withExpiresAt(expirationDate(tokenType))
                    .withClaim(CLAIM_TYPE, tokenType.name())
                    .withSubject(userDetails.getUser().getId().toString())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Erro ao gerar token.", exception);
        }
    }

    public String getSubjectFromToken(String token) {
        return getSubjectFromToken(token, null);
    }

    public String getSubjectFromToken(String token, TokenType expectedType) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token);

            if (expectedType != null) {
                String tokenType = decodedJWT.getClaim(CLAIM_TYPE).asString();
                if (!expectedType.name().equals(tokenType)) {
                    throw new JWTVerificationException("Tipo de token inválido.");
                }
            }

            return decodedJWT.getSubject();
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Token inválido ou expirado.");
        }
    }

    public boolean isTokenValid(String token, TokenType expectedType) {
        try {
            getSubjectFromToken(token, expectedType);
            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }

    private Instant expirationDate(TokenType tokenType) {
        Duration duration = tokenType == TokenType.REFRESH ? REFRESH_TOKEN_DURATION : ACCESS_TOKEN_DURATION;
        return Instant.now().plus(duration);
    }
}
