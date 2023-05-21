package com.example.pincommunity.configurations;

import com.example.pincommunity.servicies.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {
    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }


    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**","/swagger-ui.html", "/v3/api-docs",
            "/pictures", "/pins","/pinsets",
            "/members","/login", "/registration","/clubs", "/avatars/**"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
       return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userService);
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().and()
                .authorizeRequests(authorize -> authorize

                        .mvcMatchers("/resources/**","/login","/home","/registration","/swagger-ui/index.html").permitAll()
                    //    .mvcMatchers("/**").authenticated()
//                        .mvcMatchers("/").permitAll()
//                        .mvcMatchers("/registration").permitAll()

                )
                .cors().and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .successForwardUrl("/loginpost")
              // .defaultSuccessUrl("/", true)
                .and()
                .logout()
                .and()
                .httpBasic(withDefaults());
        return http.build();
    }

}
