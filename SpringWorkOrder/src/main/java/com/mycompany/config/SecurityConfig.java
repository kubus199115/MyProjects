/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.config;

import javax.sql.DataSource;
import javax.ws.rs.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;

/**
 *
 * @author Kubus
 */
@Configuration
@EnableWebMvcSecurity
public class SecurityConfig
    extends WebSecurityConfigurerAdapter{
    
    @Autowired
    DataSource dataSource;
    
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .formLogin()
      .and()
        .logout()
          .logoutSuccessUrl("/")
      .and()
      .rememberMe()
        .tokenRepository(new InMemoryTokenRepositoryImpl())
        .tokenValiditySeconds(2419200)
        .key("spittrKey")
      .and()
       .httpBasic()
         .realmName("Spittr")
      .and()
      .authorizeRequests()      //najpierw najbardziej ograniczone
        .antMatchers("/register").permitAll()
        .antMatchers("/addWorkOrder").hasAnyAuthority("PLAN_ROLE", "ENGIN_ROLE")
        .antMatchers("/workOrderPanel").hasAnyAuthority("PLAN_ROLE", "ENGIN_ROLE")
        .antMatchers("/workOrderPanel.xls").hasAnyAuthority("PLAN_ROLE", "ENGIN_ROLE")
        .antMatchers("/workOrderPanel/{field}/{order}").hasAnyAuthority("PLAN_ROLE", "ENGIN_ROLE")
        .antMatchers("/workOrderPanel/{field}/{order}.xls").hasAnyAuthority("PLAN_ROLE", "ENGIN_ROLE")
        .antMatchers("/workOrder/edit/{woNumber}").hasAnyAuthority("PLAN_ROLE", "ENGIN_ROLE")
        .antMatchers("/workOrder/addFromFile").hasAnyAuthority("PLAN_ROLE", "ENGIN_ROLE")
        .antMatchers("/workOrder/addFromFile.xls").hasAnyAuthority("PLAN_ROLE", "ENGIN_ROLE")
        .antMatchers("/woPanelOption").hasAnyAuthority("PLAN_ROLE", "ENGIN_ROLE")
        .antMatchers(HttpMethod.POST, "/workOrder/{woNumber}").hasAnyAuthority("ENGIN_ROLE","PLAN_ROLE")   // remove users from work order
        .antMatchers(HttpMethod.POST, "/workOrder/{woNumber}/{page}").hasAnyAuthority("ENGIN_ROLE","PLAN_ROLE")   // remove users from work order
        .antMatchers(HttpMethod.GET, "/workOrder/addMechanic/{woNumber}").hasAnyAuthority("ENGIN_ROLE", "PLAN_ROLE")    // czemu to nie działa????????????
        .antMatchers(HttpMethod.POST, "/workOrder/addMechanic/{woNumber}").hasAnyAuthority("ENGIN_ROLE", "PLAN_ROLE")
        .antMatchers("/").hasAnyAuthority("MECHAN_ROLE", "PLAN_ROLE", "ENGIN_ROLE")
        .antMatchers("/{page}").hasAnyAuthority("MECHAN_ROLE", "PLAN_ROLE", "ENGIN_ROLE")
        .antMatchers(HttpMethod.GET, "/workOrder/{woNumber}").hasAnyAuthority("MECHAN_ROLE", "PLAN_ROLE", "ENGIN_ROLE")
        .antMatchers(HttpMethod.GET, "/workOrder/{woNumber}/{page}").hasAnyAuthority("MECHAN_ROLE", "PLAN_ROLE", "ENGIN_ROLE")
        .antMatchers(HttpMethod.GET, "/workOrder/joinToWO/{woNumber}").hasAnyAuthority("MECHAN_ROLE", "PLAN_ROLE", "ENGIN_ROLE")
        .antMatchers(HttpMethod.GET, "/workOrder/start/{woNumber}").hasAnyAuthority("MECHAN_ROLE", "PLAN_ROLE", "ENGIN_ROLE")
        .antMatchers(HttpMethod.GET, "/workOrder/stop/{woNumber}").hasAnyAuthority("MECHAN_ROLE", "PLAN_ROLE", "ENGIN_ROLE")
        .antMatchers(HttpMethod.GET, "/workOrder/resume/{woNumber}").hasAnyAuthority("MECHAN_ROLE", "PLAN_ROLE", "ENGIN_ROLE")
        .antMatchers(HttpMethod.GET, "/workOrder/pause/{woNumber}/{type}").hasAnyAuthority("MECHAN_ROLE", "PLAN_ROLE", "ENGIN_ROLE")
        .antMatchers(HttpMethod.GET, "/workOrder/addComment/{woNumber}/{type}").hasAnyAuthority("MECHAN_ROLE", "PLAN_ROLE", "ENGIN_ROLE")
        .antMatchers(HttpMethod.POST, "/workOrder/pause/{woNumber}/{type}").hasAnyAuthority("MECHAN_ROLE", "PLAN_ROLE", "ENGIN_ROLE")
        .antMatchers(HttpMethod.POST, "/workOrder/addComment/{woNumber}/{type}").hasAnyAuthority("MECHAN_ROLE", "PLAN_ROLE", "ENGIN_ROLE")
        .antMatchers("/searchWorkOrder").hasAnyAuthority("MECHAN_ROLE", "PLAN_ROLE", "ENGIN_ROLE")
        
        .anyRequest().permitAll();
      
  }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       
        auth.jdbcAuthentication().dataSource(dataSource)
                .passwordEncoder(new StandardPasswordEncoder("53cr3t"));
    }
 }
