package com.kruczek.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


/**
 * Created by Patryk on 2017-10-30.
 */

@Configuration
@EnableWebSecurity
@ComponentScan
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("patryk").password("1234").roles("USER")
                .and()
                .withUser("admin").password("1234").roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

                .authorizeRequests()
                .antMatchers("/list-customers").hasAnyRole("USER", "ADMIN")
                .antMatchers("/", "/resources/**").permitAll()
                .anyRequest().authenticated()

                .and()
                .formLogin().loginPage("/loginForm").permitAll()
                .passwordParameter("pass")
                .usernameParameter("user")
                .and()
                .logout()
                .logoutUrl("/logoutPage")
                .logoutSuccessUrl("/logoutPage").permitAll();


    }

}