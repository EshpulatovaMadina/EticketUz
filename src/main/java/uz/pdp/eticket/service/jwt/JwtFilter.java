package uz.pdp.eticket.service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private JwtService jwtService;
    private AuthenticationService authenticationService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if(authorization == null || !authorization.startsWith("Bearer")) {
            filterChain.doFilter(request,response);
            return;
        }

        try{
            String token = authorization.substring(7);
            Jws<Claims> claimsJws = jwtService.extractToken(token);
            authenticationService.authenticate(claimsJws.getBody(),request);
            filterChain.doFilter(request,response);
        }catch (ExpiredJwtException e) {
            try {
                throw  new AuthException("Token expired");
            } catch (AuthException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
