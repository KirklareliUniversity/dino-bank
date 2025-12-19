package com.dinobank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public BCryptPasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource)
                        throws Exception {

                http
                                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                                .csrf(csrf -> csrf.disable())

                                // TODO
                                // BURASI ÖNEMLİ 403 HATASI ALIYORUZ O YÜZDEN ŞİMDİLİK BÖYLE BİR MESAJ EKLEDİM
                                .exceptionHandling(exception -> exception
                                                .authenticationEntryPoint((request, response, authException) -> {
                                                        response.setContentType("application/json;charset=UTF-8");
                                                        response.setStatus(403);
                                                        response.getWriter().write(
                                                                        "{\"message\": \"Erişim Reddedildi (Yetkisiz İşlem)\"}");
                                                }))

                                // Allow frames for H2 Console
                                .headers(headers -> headers.frameOptions(frame -> frame.disable()))

                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                                .requestMatchers("/", "/index.html", "/login.html", "/register.html",
                                                                "/dashboard.html",
                                                                "/vite.svg")
                                                .permitAll()
                                                .requestMatchers("/css/**", "/js/**", "/img/**", "/assets/**")
                                                .permitAll()
                                                .requestMatchers("/error").permitAll()
                                                .requestMatchers("/h2-console/**").permitAll()
                                                // Frontend Routes (SPA)
                                                .requestMatchers("/dashboard", "/cards", "/invest", "/history",
                                                                "/settings", "/admin", "/login",
                                                                "/register")
                                                .permitAll()

                                                .requestMatchers("/api/auth/**").permitAll()
                                                .requestMatchers("/api/customers/register").permitAll()
                                                .requestMatchers("/api/accounts/**").permitAll()
                                                .requestMatchers("/api/transactions/**").permitAll()
                                                .requestMatchers("/api/admin/**").permitAll()
                                                .requestMatchers("/api/credits/**").permitAll() // Allow Credit API

                                                .anyRequest().authenticated());

                return http.build();
        }
}