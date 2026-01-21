package com.example.blogging.security;

import com.example.blogging.security.service.RoleHierarchyService;
import com.example.blogging.security.service.impl.CustomUserDetailsService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtAuthenticationFilter
    ) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterAfter(jwtAuthenticationFilter, LogoutFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            CustomUserDetailsService customUserDetailsService,
            PasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtAuthFilterRegistration(JwtAuthenticationFilter filter) {
        FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>(filter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }

    @Bean
    public DynamicRoleHierarchy dynamicRoleHierarchy(RoleHierarchyService roleHierarchyService) {
        System.out.println(roleHierarchyService.getHierarchy());
        return new DynamicRoleHierarchy(roleHierarchyService.getHierarchy());
    }

    @Bean
    public DefaultWebSecurityExpressionHandler expressionHandler(DynamicRoleHierarchy dynamicRoleHierarchy) {
        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(dynamicRoleHierarchy);
        return expressionHandler;
    }
}
