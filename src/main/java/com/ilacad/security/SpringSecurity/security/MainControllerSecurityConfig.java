package com.ilacad.security.SpringSecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableMethodSecurity
public class MainControllerSecurityConfig {

    @Bean
    public UserDetailsManager userDetailsManager(DataSource datasource) {

        return new JdbcUserDetailsManager(datasource);

    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(configurer -> configurer
                        .requestMatchers("/").hasRole("EMPLOYEE")
                        .requestMatchers("/leaders/**").hasRole("MANAGER")
                        .requestMatchers("/systems/**").hasRole("ADMIN")
                        .anyRequest()
                        .authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login-page")
                        .loginProcessingUrl("/authenticate-user")
                        .permitAll()
                )
                .logout(logout -> logout.permitAll())
                .exceptionHandling(configurer -> configurer
                        .accessDeniedPage("/access-denied")
                );

        return http.build();

    }

    //    @Bean
//    public InMemoryUserDetailsManager userDetailsManager() {
//
//        UserDetails john = User.builder().username("john").password("{noop}test123").roles("EMPLOYEE").build();
//
//        UserDetails mary = User.builder().username("mary").password("{noop}test123").roles("EMPLOYEE", "MANAGER").build();
//
//        UserDetails susan = User.builder().username("susan").password("{noop}test123").roles("EMPLOYEE", "MANAGER", "ADMINISTRATOR").build();
//
//        UserDetails jc = User.builder().username("jc").password("{noop}jcilacad").roles("EMPLOYEE","ADMINISTRATOR").build();
//
//
//        return new InMemoryUserDetailsManager(john, mary, susan, jc);
//    }


}
