package com.example.backend.config;

import com.example.backend.Jwt.JwtAuthenticationFilter;
import com.example.backend.Jwt.JwtUtil;
import com.example.backend.entity.maria.enumData.Authority;
import com.example.backend.security.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class CustomSecurityConfig  {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailService customUserDetailService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(CsrfConfigurer::disable)
                .cors(CorsConfigurer::disable)
                .authorizeHttpRequests((authorizeRequests)->
                        authorizeRequests
                                .requestMatchers("/**").permitAll()
                                .requestMatchers("/join/**").hasAuthority(Authority.ADMIN.name())
                                .anyRequest().authenticated())
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil, customUserDetailService), UsernamePasswordAuthenticationFilter.class)
                .formLogin((formLogin) ->
                    formLogin
                            .loginPage("/login")
                            .usernameParameter("email")
                            .passwordParameter("password")
                            .defaultSuccessUrl("/")
                            .failureUrl("/")

                )
                .logout((logoutConfig)-> logoutConfig.logoutSuccessUrl("/login")
        ).userDetailsService(customUserDetailService);

        return http.build();
    }
}
