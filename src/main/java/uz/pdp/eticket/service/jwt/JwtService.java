package uz.pdp.eticket.service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import uz.pdp.eticket.entity.UserEntity;

import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
    @Value("${jwt.expiry}")
    private Integer expiry;

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken (UserEntity user) {
        if(user.isEnabled()) {
            Date iat = new Date();

            return Jwts.builder()
                    .setSubject(user.getId().toString())
                    .setIssuedAt(iat)
                    .setExpiration(new Date(iat.getTime() + expiry))
                    .addClaims(getAuthorities(user))
                    .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                    .compact();
        }
        throw new AuthenticationCredentialsNotFoundException("User is not active");
    }

    public Jws<Claims> extractToken (String token) {
        return  Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseClaimsJws(token);
    }

    private Map<String, Object> getAuthorities(UserEntity user) {
        return Map.of("roles",
                user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList()
                );
    }
}
