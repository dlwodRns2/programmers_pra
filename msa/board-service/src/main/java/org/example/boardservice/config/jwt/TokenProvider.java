package org.example.boardservice.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.boardservice.config.security.CustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenProvider {

    private static final String CLAIM_ID = "id";
    private static final String CLAIM_NAME = "name";
    private static final String CLAIM_ROLE = "role";

    private final JwtProperties jwtProperties;

    private SecretKey secretKey;
    private JwtParser jwtParser;

    @PostConstruct
    private void init(){
        this.secretKey= Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtProperties.getSecretKey()));
        this.jwtParser= Jwts.parser().verifyWith(secretKey).build();
    }

    public TokenStatus validateToken(String token){
        try{
            jwtParser.parseSignedClaims(token);
            log.debug("Token is valid");
            return TokenStatus.VALID;
        }catch (ExpiredJwtException e){
            log.warn("Token is expired");
            return TokenStatus.EXPIRED;
        }catch (Exception e) {
            log.warn("Token is invalid");
            return TokenStatus.INVALID;
        }
    }

    public CustomUserDetails getTokenDetails(String token){
        Claims claims = getClaims(token);
        return CustomUserDetails.builder()
                .id(claims.get(CLAIM_ID,Long.class))
                .userId(claims.getSubject())
                .userName(claims.get(CLAIM_NAME,String.class))
                .role(claims.get(CLAIM_ROLE,String.class))
                .build();
    }
    private Claims getClaims(String token){
        return jwtParser
                .parseSignedClaims(token)
                .getPayload();
    }

    public Authentication getAuthentication(CustomUserDetails principal, String token ){
        return new UsernamePasswordAuthenticationToken(principal,token,principal.getAuthorities());
    }
}
