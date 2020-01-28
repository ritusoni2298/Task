package com.emailpart.emaildemo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/confirm").permitAll()
                .antMatchers("/dashboard").hasAnyRole("USER","ADMIN")
                .antMatchers("/delete/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/loginform")
                .usernameParameter("emailId").
                passwordParameter("password")
                .defaultSuccessUrl("/dashboard").permitAll()
                .and()
                .csrf();
    }



    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception {
        authenticationMgr.inMemoryAuthentication()
                .withUser("ritusoni2298@gmail.com").password("{noop}ritu").roles("USER")
                .and()
                .withUser("soni.ritu22198@gmail.com").password("{noop}ritu").roles("ADMIN");
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//
//        http.authorizeRequests()
//                .antMatchers("/homePage").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
//                .antMatchers("/userPage").access("hasRole('ROLE_USER')")
//                .antMatchers("/adminPage").access("hasRole('ROLE_ADMIN')")
//                .and()
//                .formLogin().loginPage("/loginPage")
//                .defaultSuccessUrl("/homePage")
//                .failureUrl("/loginPage?error")
//                .usernameParameter("username").passwordParameter("password")
//                .and()
//                .logout().logoutSuccessUrl("/loginPage?logout");
//
//    }
}
