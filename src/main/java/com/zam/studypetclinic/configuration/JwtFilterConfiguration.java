package com.zam.studypetclinic.configuration;

import com.zam.studypetclinic.entity.Admin;
import com.zam.studypetclinic.entity.Doctor;
import com.zam.studypetclinic.entity.User;
import com.zam.studypetclinic.services.JwtService;
import com.zam.studypetclinic.services.impl.AdminServiceImpl;
import com.zam.studypetclinic.services.impl.DoctorServiceImpl;
import com.zam.studypetclinic.services.impl.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtFilterConfiguration extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    private DoctorServiceImpl doctorService;

    @Autowired
    private UserServiceImpl userService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader("Authorization");
        final String jwtToken;
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwtToken = header.substring(7);
        String username = jwtService.extractUsername(jwtToken);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            String authority = jwtService.extractAuthorities(jwtToken);
            if(authority.equalsIgnoreCase("ADMIN")){
                Admin userByUsername = this.adminService.loadUserByUsername(username);
                if (jwtService.isTokenValid(jwtToken, userByUsername)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userByUsername.getUsername(),
                                    null,
                                    userByUsername.getAuthorities()
                            );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }else if(authority.equalsIgnoreCase("DOCTOR")){
                Doctor userByUsername = this.doctorService.loadUserByUsername(username);
                if (jwtService.isTokenValid(jwtToken, userByUsername)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userByUsername.getUsername(),
                                    null,
                                    userByUsername.getAuthorities()
                            );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }else{
                User userByUsername = this.userService.loadUserByUsername(username);
                if (jwtService.isTokenValid(jwtToken, userByUsername)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userByUsername.getUsername(),
                                    null,
                                    userByUsername.getAuthorities()
                            );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
