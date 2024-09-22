package com.prueba.lexart.config;


import com.prueba.lexart.security.jwt.JWTConfigurer;
import com.prueba.lexart.security.jwt.TokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JWTConfigurer jwtConfigurer = new JWTConfigurer(tokenProvider());

        http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(a -> a.requestMatchers(
                                "/api/users/authenticate",
                                "/h2-console/**",
                                "/swagger-ui/**",
                                "/webjars/**",
                                "/",
                                "/users/**",
                                "/v3/api-docs/**")
                        .permitAll()
                        .requestMatchers(
                                "/api/users/**",
                                "/products/**").authenticated())
                .sessionManagement(h -> h.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(headers -> headers.permissionsPolicy(policy -> policy.policy("frame-src 'self'")))
                .apply(jwtConfigurer);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenProvider tokenProvider() {
        return new TokenProvider(secret, expiration);
    }
}
