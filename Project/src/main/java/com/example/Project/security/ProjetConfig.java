package com.example.Project.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;




@Configuration
@EnableWebSecurity
public class ProjetConfig {


   @Bean
    public UserDetailsService userDetailsService() {
       var userDetailsService = new InMemoryUserDetailsManager();
       var user = User.withUsername("john")
               .password("1234")
               .authorities("read")
               .roles("user")
               .build();
       userDetailsService.createUser(user);

       var user1 = User.withUsername("admin").password("1234")
               .authorities("read", "write").roles("user", "admin").build();
       userDetailsService.createUser(user1);
       return userDetailsService;
   }


   protected void configure(HttpSecurity http) throws Exception{

       http.authorizeRequests().anyRequest().permitAll();

   }
}
