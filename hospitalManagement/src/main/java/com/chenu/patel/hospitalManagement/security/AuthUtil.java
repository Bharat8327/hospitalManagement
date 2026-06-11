package com.chenu.patel.hospitalManagement.security;

import com.chenu.patel.hospitalManagement.entity.User;
import com.chenu.patel.hospitalManagement.entity.type.AuthProviderType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Slf4j
public class AuthUtil {

    @Value("${spring.secretKey}")
    private String secretKey;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(
                secretKey.getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generateJwtToken(User user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("userId", user.getId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
                .signWith(getSecretKey())
                .compact();
    }

    public String getUsernameFromToken(String token) {
           Claims claims=  Jwts.parser()
                        .verifyWith( getSecretKey() )
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();
           return claims.getSubject();
    }

    public AuthProviderType getAuthProviderTypeAndRegistractionrId(String registertionId) {
            return switch (registertionId.toLowerCase()){
                case "google" -> AuthProviderType.GOOGLE;
                case "facebook" -> AuthProviderType.FACEBOOK;
                case "twitter" -> AuthProviderType.TWITTER;
                case "github" -> AuthProviderType.GITHUB;
                default -> throw new IllegalArgumentException("Unsupported OAuth2 provider"+registertionId);
            };
    }

    public String determineProviderIdFromOAuth2User(OAuth2User oAuth2User , String registertionId ) {
        String providerId = switch (registertionId.toLowerCase()){
            case "google" -> oAuth2User.getAttribute("sub");
            case "github" -> oAuth2User.getAttribute("id").toString();
            default -> {
                log.error("Unsupported OAuth2 provider "+registertionId);
                throw new IllegalArgumentException("Unsupported OAuth2 provider "+registertionId);
            }
        };

        if(providerId==null||providerId.isBlank()){
                log.error("Unable to determine providerId for provider "+registertionId);
                throw new IllegalArgumentException("Unable to determine providerId for OAuth2 login "+registertionId);
        }
        return providerId;
    }

    public String determineUsernameFromOAuth2User(OAuth2User oAuth2User , String registertionId , String providerId ) {
        String email = oAuth2User.getAttribute("email");
        if(email!=null&&!email.isBlank()){
            return email;
        }
        return switch (registertionId.toLowerCase()){
            case "google" -> oAuth2User.getAttribute("sub");
            case "github" -> oAuth2User.getAttribute("login");
            default -> providerId;
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


}

