package ru.sanctio.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class MySecurityConfig {
//    @Bean
//    public UserDetailsManager userDetailsManager(DataSource dataSource) {
//        return new JdbcUserDetailsManager(dataSource);
//    }

    //Хранение данных на уровне приложения
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(BCryptPasswordEncoder passwordEncoder) {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("SHEM")
                .password(passwordEncoder.encode("SHEM"))
                .roles("EMPLOYEE")
                .build());
        manager.createUser(User.withUsername("Yana")
                .password(passwordEncoder.encode("123qwe"))
                .roles("HR")
                .build());
        manager.createUser(User.withUsername("Mark")
                .password(passwordEncoder.encode("123qwe"))
                .roles("MANAGER", "HR")
                .build());
        return manager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((user) -> user
                .requestMatchers(new AntPathRequestMatcher("/api/clients")).hasAnyRole("HR", "MANAGER", "EMPLOYEE")
                .requestMatchers(new AntPathRequestMatcher("/api/clients/action/**")).hasRole("MANAGER")
//                .requestMatchers(new AntPathRequestMatcher("/hr_info/**")).hasRole("HR")
                .anyRequest().authenticated()
        ).formLogin(Customizer.withDefaults());


        return http.build();
    }
}
