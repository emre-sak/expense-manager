package com.expensemanager.config;

import com.expensemanager.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
public class CustomSecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((requests) -> {
            requests.requestMatchers("/", "/login", "/register").permitAll();
            requests.requestMatchers("/resources/**","/css/**", "/js/**","/css/images/**").permitAll();
            requests.anyRequest().authenticated();
        }).formLogin((formLogin) -> {
            formLogin.loginPage("/login").permitAll();
            formLogin.failureUrl("/login?error=true");
            formLogin.defaultSuccessUrl("/expenses");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");
        }).logout((logout) -> {
            logout.invalidateHttpSession(true);
            logout.clearAuthentication(true);
            logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
            logout.logoutSuccessUrl("/login?logout").permitAll();
        });

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
