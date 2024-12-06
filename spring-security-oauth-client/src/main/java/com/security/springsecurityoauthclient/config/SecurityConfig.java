package com.security.springsecurityoauthclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

/**
 * Security Configuration class for defining HTTP security policies and password encoding.
 * <p>
 * Supports OAuth2 login and token-based authentication.
 *
 * @author Richa Pokhrel
 */
@Component
@EnableWebSecurity
public class SecurityConfig {

    private static final String[] WHITE_LIST_URLS = {
            "/register",
            "/verify-registration*",
            "/resend/verification-token*"
    };

    /**
     * Bean for password encoding using BCrypt with a strength of 11.
     *
     * @return {@link PasswordEncoder} instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(16);
    }

    /**
     * Configures the HTTP security policies, including endpoint access permissions,
     * CSRF handling, CORS settings, and OAuth2 login integration.
     *
     * @param http the {@link HttpSecurity} to modify.
     * @return the configured {@link SecurityFilterChain}.
     * @throws Exception if any configuration error occurs.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Example: Disabling CSRF for APIs
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.defaultSuccessUrl("/home")) // Form login configuration
                .logout(logout -> logout.logoutSuccessUrl("/logout-success")); // Logout configuration

        return http.build();
    }

}
