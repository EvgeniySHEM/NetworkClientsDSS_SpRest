package ru.sanctio.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class MySecurityConfig {

    //хранение данных в БД
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
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
