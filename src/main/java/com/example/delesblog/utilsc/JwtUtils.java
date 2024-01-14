package com.example.delesblog.utilsc;

import com.google.common.base.Objects;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;


// main purpose of this class is for the creation ofJWToken
// extract username and expirationDate from token //before that, extract class
// check if token is valid or expired
@Component
public class JwtUtils {

    private final Supplier<SecretKeySpec> getKey = () -> {
        Key key = Keys.hmacShaKeyFor("7f074d0943101aaa2725afa339f4a983ed8925675c5f1339e134b07fe3996306f01f631b46b29dc46ee37b69e56582f3308749549c9ec25d9fbea032209e4bc2"
                .getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec(key.getEncoded(), key.getAlgorithm());
    };

    private final Supplier<Date> expirationTime = () ->
            Date.from(LocalDateTime.now()
                    .plusMinutes(10)
                    .atZone(ZoneId.systemDefault())
                    .toInstant());

    public Function<UserDetails,String> createJwt = (userDetails) -> {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .signWith(getKey.get())
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expirationTime.get())
                .compact();


    };
//2nd - extract username and expirationDate from token - //before that, extract claim
    public <T> T extractClaim(String token, Function<Claims, T> claimResolver){
        Claims claims = Jwts.parser().verifyWith(getKey.get()).build().parseSignedClaims(token)
                .getPayload();
        return claimResolver.apply(claims);
    }

    public Function<String, String> extractUsername = (token) -> extractClaim(token, Claims::getSubject);

    public Function<String, Date> extractExpirationDate = (token) -> extractClaim(token, Claims::getExpiration);

            //3rd - check if token isValid and is Expired
    public Function<String, Boolean> isTokenExpired = (token) ->extractExpirationDate.apply(token).after(new Date(System.currentTimeMillis()));
    public BiFunction<String,String, Boolean> isTokenValid = (token, username) -> isTokenExpired.apply(token) &&
            Objects.equal(extractUsername.apply(token), username);
}
