package org.example.oauth2.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.oauth2.config.security.CustomUserDetails;
import org.example.oauth2.domain.entity.Role;
import org.example.oauth2.domain.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

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
        this.jwtParser = Jwts.parser().verifyWith(secretKey).build();
    }
    public String generateToken(User user, Duration expiredAt){
        Date now = new Date();
        return makeToken(
                user,
                new Date(now.getTime()+expiredAt.toMillis())
        );
    }
    private String makeToken(User user, Date expire){
        return Jwts.builder()
                .header().type("JWT").and()
                .issuer(jwtProperties.getIssuer())
                .issuedAt(new Date())
                .expiration(expire)
                .subject(user.getUserId())
                .claim(CLAIM_ID,user.getId())
                .claim(CLAIM_NAME,user.getName())
                .claim(CLAIM_ROLE,user.getRole())
                .signWith(secretKey,Jwts.SIG.HS512)
                .compact();
    }
    public TokenStatus validateToken(String token){
        try{
            jwtParser.parseSignedClaims(token);
            log.debug("Token is valid");
            return TokenStatus.VALID;
        }catch (ExpiredJwtException e){
            log.warn("Token is expired");
            return TokenStatus.EXPIRED;
        }catch (Exception e){
            log.warn("Token is invalid");
            return TokenStatus.INVALID;
        }
    }

    public User getTokenDetails(String token){
        Claims claims = getClaims(token);
        return User.builder()
                .id(claims.get(CLAIM_ID, Long.class))
                .userId(claims.getSubject())
                .name(claims.get(CLAIM_NAME, String.class))
                .role(Role.valueOf(claims.get(CLAIM_ROLE, String.class)))
                .build();
    }
    public Claims getClaims(String token){
        return jwtParser
                .parseSignedClaims(token)
                .getPayload();
    }

    public Authentication getAuthentication(User user,String token){
        CustomUserDetails principal = CustomUserDetails.builder()
                .user(user)
                .build();

        return new UsernamePasswordAuthenticationToken(principal,token,principal.getAuthorities());
    }
}
