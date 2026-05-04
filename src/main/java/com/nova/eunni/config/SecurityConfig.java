package com.nova.eunni.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. CSRF 비활성화 (API 방식이므로 필수)
                .csrf(csrf -> csrf.disable())

                // 2. CORS 설정 추가 (프론트엔드 포트가 3000일 경우 필수)
                .cors(cors -> cors.configurationSource(request -> {
                    var config = new org.springframework.web.cors.CorsConfiguration();
                    config.setAllowedOrigins(java.util.List.of("http://localhost:3000"));
                    config.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(java.util.List.of("*"));
                    config.setAllowCredentials(true);
                    return config;
                }))

                // 3. 권한 설정
                .authorizeHttpRequests(auth -> auth
                        // 회원가입, 이메일 인증, 로그인은 누구나 접근 가능해야 함
                        .requestMatchers("/api/signup/**", "/api/auth/**").permitAll()

                        // 관리자 관련 API는 권한이 필요함 (나중에 적용)
                        // .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // 현재는 테스트를 위해 모든 api 허용 상태 유지
                        .requestMatchers("/api/**").permitAll()

                        .anyRequest().authenticated()
                )

                // 4. 세션/폼 로그인 비활성화 (JWT 사용 예정이라면 세션 정책도 STATELESS 권장)
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable());

        return http.build();
    }
}