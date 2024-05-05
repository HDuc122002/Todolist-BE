package com.todoList.toDoList.config;

import com.todoList.toDoList.security.filter.JwtAuthenticationFilter;
import com.todoList.toDoList.security.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Value("${api.prefix}")
    public String apiPrefix;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        request->{
                            request
                                    .requestMatchers(
                                            String.format("%s/registration",apiPrefix),
                                            String.format("%s/login",apiPrefix)
                                    )
                                    .permitAll()
                                    .requestMatchers(String.format("%s/tasks/**",apiPrefix)).hasAuthority("USER")
                                    .requestMatchers(String.format("%s/tasks/create/**",apiPrefix)).hasAuthority("USER")
                                    .requestMatchers(String.format("%s/tasks/update/**",apiPrefix)).hasAuthority("USER")
                                    .requestMatchers(String.format("%s/tasks/delete/**",apiPrefix)).hasAuthority("USER")
                                    .requestMatchers(String.format("%s/users/**",apiPrefix)).hasAuthority("ADMIN")
                                    .requestMatchers(String.format("%s/users/create",apiPrefix)).hasAuthority("ADMIN")
                                    .requestMatchers(String.format("%s/users/update/**",apiPrefix)).hasAuthority("ADMIN")
                                    .requestMatchers(String.format("%s/users/delete/**",apiPrefix)).hasAuthority("ADMIN")
                                    .anyRequest().authenticated();
                        }
                )

                .userDetailsService(userDetailsServiceImpl)
                .sessionManagement(
                    session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                .build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
