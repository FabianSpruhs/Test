package de.haegerconsulting.haegermanagement.configurations.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAthFilter jwtAthFilter;
    private final MyBasicAuthenticationEntryPoint myBasicAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http    .cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/employee/admin/**").hasRole("ADMIN")
                .antMatchers("/api/employee/accounting/**").hasAnyRole("ADMIN", "ACCOUNTING")
                .antMatchers("/api/client/accounting/**").hasAnyRole("ADMIN", "ACCOUNTING")
                .antMatchers("/api/project/accounting/**").hasAnyRole("ADMIN", "ACCOUNTING")
                .antMatchers("/api/sick_note/accounting/**").hasAnyRole("ADMIN", "ACCOUNTING")
                .antMatchers("/api/vacation_request/accounting/**").hasAnyRole("ADMIN", "ACCOUNTING")
                .antMatchers("/api/working_hour/accounting/**").hasAnyRole("ADMIN", "ACCOUNTING")
                .antMatchers("/api/auth/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/api/auth").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAthFilter, UsernamePasswordAuthenticationFilter.class).httpBasic().authenticationEntryPoint(myBasicAuthenticationEntryPoint);
        return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }



}
