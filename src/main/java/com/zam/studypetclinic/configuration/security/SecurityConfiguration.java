package com.zam.studypetclinic.configuration.security;
import com.zam.studypetclinic.configuration.JwtFilterConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {
    @Autowired
    private JwtFilterConfiguration jwtFilterConfiguration;
    @Autowired
    private AuthenticationManager authenticationManager;
    // end point unsecure
    public String[] WHITE_LIST_GLOBAL = {
            "/api/v1/unsecure/pet/**",
            "/api/v1/unsecure/**",
            "/api/v1/auth/**",
            "/api/v1/auth/admin/**",
            "/api/v1/secure/test",
            "/api/v1/doctor/all",
            "/api/v1/doctor/auth/signup",
            "/api/v1/doctor/auth/authenticate",
            "/api/v1/auth/user/**",
            "/api/v1/"
    };
    public String[] CAN_ACCESS_ADMIN = {
            "/api/v1/secure/admin/category/**",
            "/api/v1/secure/admin/pet/**",
            "/api/v1/employee/add",
            "/api/v1/secure/admin/doctor/**"
    };

    public String[] CAN_ACCESS_DOCTOR = {

    };

    public String[] CAN_ACCESS_DOCTOR_AND_ADMIN = {
            "/api/v1/doctor/update"
    };

    public String[] AUTHORITIES_ADMIN_AND_DOCTOR = {
        "DOCTOR" ,"ADMIN"
    };
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(WHITE_LIST_GLOBAL)
                .permitAll()
                .requestMatchers(CAN_ACCESS_ADMIN).hasAuthority("ADMIN")
                .requestMatchers(CAN_ACCESS_DOCTOR).hasAuthority("DOCTOR")
                .requestMatchers(CAN_ACCESS_DOCTOR_AND_ADMIN).hasAuthority("DOCTOR")
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationManager(authenticationManager)
                .addFilterBefore(jwtFilterConfiguration, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
