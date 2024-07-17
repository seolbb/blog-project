package org.example.blog_project.config;

import lombok.RequiredArgsConstructor;
import org.example.blog_project.security.CustomUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/","/userregform","/userreg","/welcome", "/api/users/check-username", "/api/users/check-email", "/css/**", "/api/posts").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/loginform")
                        .loginProcessingUrl("/login")
                        .successHandler(authenticationSuccessHandler()) // 로그인 성공 핸들러 설정
                        .failureUrl("/loginerror")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")

                )
                .sessionManagement(sessionManagement -> sessionManagement
                        .maximumSessions(1) //동시 접속 허용 개수
                        .maxSessionsPreventsLogin(true) //동시 로그인을 차단 defalt - false (먼저 로그인한 사용자 차단) / true - 애초에 허용개수를 초과하는 사용자는 로그인이 안되도록 차단.
                )
                .userDetailsService(customUserDetailsService)
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // 로그인 성공시 처리 로직
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            String username = authentication.getName();
            response.sendRedirect("/"); // 로그인 성공 시 리다이렉트 URL 설정
        };
    }
}
