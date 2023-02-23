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
    };
    public String[] CAN_ACCESS_ADMIN = {
            "/api/v1/secure/admin/category/**",
            "/api/v1/secure/admin/pet/**",
    };

    public String[] CAN_ACCESS_EMPLOYEE = {

    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(WHITE_LIST_GLOBAL)
                .permitAll()
                .requestMatchers(CAN_ACCESS_ADMIN).hasAuthority("ADMIN")
                .requestMatchers(CAN_ACCESS_EMPLOYEE).hasAuthority("EMPLOYEE")
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
