package com.elefants.shorturl.jwt;

import com.elefants.shorturl.users.auth.AuthenticationService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtUtils {

    @Value("${demo.app.jwtSecret}")
    private String jwtSecret;

    @Value("${demo.app.jwtExpirationMs}")
    private int jwtExpirationMs;


    private final AuthenticationService authenticationService;


    public String generateJwtToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        if(userDetails instanceof User customUserDetails){
            claims.put("username", customUserDetails.getUsername());
            claims.put("role", customUserDetails.getRoles().next());
        }

        return generateJwtToken(claims, userDetails);

    }


    public Claims getUserRolesFromJwtToken(String token) {
        return Jwts.parser().verifyWith(key()).build()
                .parseSignedClaims(token).getPayload();
    }
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = getUserNameFromJwtToken(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // TOKEN INFO
    public String getUserNameFromJwtToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers){
        final Claims claims = extractAllClaim(token);
        return claimsResolvers.apply(claims);
    }

    private Claims extractAllClaim(String token) {
        return Jwts.parser().verifyWith(key()).build().parseSignedClaims(token)
                .getPayload();
    }

    private String generateJwtToken(Map<String, Object> extraClaims, UserDetails userDetails){
        return Jwts.builder().claims(extraClaims).subject(userDetails.getUsername())
                .issuedAt(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(key())
                .compact();
    }
    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
}
