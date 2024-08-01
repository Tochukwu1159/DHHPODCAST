package com.netshiftdigital.dhhpodcast.Security;


//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//import java.security.Key;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;


import com.netshiftdigital.dhhpodcast.exceptions.BadRequestException;
import com.netshiftdigital.dhhpodcast.exceptions.ServiceException;
import com.netshiftdigital.dhhpodcast.exceptions.TokenExpiredException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtil {
        private static final String SECRET = "59703373357638792F423F4528482B4D6251655468576D5A7134743777397A24";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {

            final Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);


    }

    private Claims extractAllClaims(String token) {
        try{
            return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        }catch (ExpiredJwtException e){
            throw new BadRequestException("Token expired 1");

        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }

    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 40000 * 60 * 24))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean isValidToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()));
    }










//        public String extractUsername(String token) {
//            return extractClaim(token, Claims::getSubject);
//        }
//
////    public static String extractUsername(String token) {
////        return Jwts.parser()
////                .setSigningKey(SECRET_KEY)
////                .parseClaimsJws(token)
////                .getBody()
////                .getSubject();
////    }
//
//
//        public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
//            final Claims claims = extractAllClaims(token);
//            return claimsResolver.apply(claims);
//        }
//
//        public Claims extractAllClaims(String token){
//
//          Claims    claims = Jwts.parserBuilder()
//                        .setSigningKey(getSignInKey())
//                        .build()
//                        .parseClaimsJws(token)
//                        .getBody();
//          return claims;
//
//            }
//
//        private Key getSignInKey(){
//            byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
//            return Keys.hmacShaKeyFor(keyBytes);
//        }
//        public String generateToken(String email){
//            return generateToken(new HashMap<>(), email);
//        }
//
//        public String generateToken(Map<String,Object> extraClaims
//                , String email){
//            return Jwts.builder()
//                    .setClaims(extraClaims)
//                    .setSubject(email)
//                    .setIssuedAt(new Date(System.currentTimeMillis()))
//                    .setExpiration(new Date(System.currentTimeMillis() + 40000 * 60 * 24))
//                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
//                    .compact();
//        }
//
//        public boolean isTokenValid(String token, UserDetails userDetails){
//            final String username = extractUsername(token);
//            return (username.equals(userDetails.getUsername()));
//        }
//
//        public boolean isTokenExpired(String token) {
//            return extractExpiration(token).before(new Date());
//        }
//
//        private Date extractExpiration(String token) {
//            return extractClaim(token, Claims::getExpiration);
//        }
    }


