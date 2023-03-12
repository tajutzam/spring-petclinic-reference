package com.zam.studypetclinic.services;

import com.zam.studypetclinic.entity.Admin;
import com.zam.studypetclinic.entity.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;


public interface JwtService {

    public String generateToken(Map<String , Object> claims , UserDetails admin);
    public String generateToken(UserDetails admin);
    public String extractUsername(String jwtToken);
    public boolean isTokenValid(String token , UserDetails admin);
    public boolean isTokenExpired(String token);
    public Claims extractAllClaims(String token);

    public Key genSigningKey();

    public <T> T extractSingleClaim(String token , Function<Claims , T> claimsResolver);

    public String generateTokenUser(Map<String , Object> claims , User user);
    public String generateTokenUser(User admin);

    public Date extractExpirationToken(String token);

    public String extractAuthorities(String token);
}
