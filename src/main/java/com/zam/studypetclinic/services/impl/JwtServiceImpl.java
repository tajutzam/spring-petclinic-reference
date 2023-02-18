package com.zam.studypetclinic.services.impl;

import com.zam.studypetclinic.entity.Admin;
import com.zam.studypetclinic.entity.User;
import com.zam.studypetclinic.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    private String secret_key = "25442A462D4A614E645267556B58703273357638792F423F4528482B4B625065" ;

    @Override
    public String generateToken(Map<String, Object> claims, Admin admin) {
        return Jwts.builder()
                .setSubject(admin.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .claim("authorities" , admin.getAuthorities())
                .claim("email" , admin.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 24 * 60))
                .signWith(genSigningKey() , SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String generateToken(Admin admin) {
        return generateToken(new HashMap<>(), admin);
    }

    @Override
    public String generateTokenUser(Map<String, Object> claims, User admin) {
        return Jwts.builder()
                .setSubject(admin.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 10000   * 24 * 60))
                .signWith(genSigningKey() , SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String generateTokenUser(User user) {
        return generateTokenUser(new HashMap<>(), user);
    }
    @Override
    public String extractUsername(String jwtToken) {
        String username = extractSingleClaim(jwtToken, Claims::getSubject);
        return username;
    }

    @Override
    public boolean isTokenValid(String token , Admin admin) {
        String username = extractUsername(token);
        return (Objects.equals(username , admin.getUsername()) && !isTokenExpired(token));
    }

    @Override
    public boolean isTokenExpired(String token) {
        return extractExpirationToken(token).before(new Date());
    }



    @Override
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(genSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public Key genSigningKey() {
        byte[] bytesKey = Decoders.BASE64.decode(this.secret_key);
        return Keys.hmacShaKeyFor(bytesKey);
    }

    @Override
    public <T> T extractSingleClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public Date extractExpirationToken(String token) {
        return extractSingleClaim(token, Claims::getExpiration);
    }
}
