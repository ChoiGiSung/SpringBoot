package com.community.rest;

import com.community.rest.event.BoardEventHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.*;

@SpringBootApplication
public class DataRestApplication {
    public static void main(String args[]){
        SpringApplication.run(DataRestApplication.class,args);
    }

    //cors허용 코드

    @Configuration
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    @EnableWebSecurity
    static class SecurityConfiguration extends WebSecurityConfigurerAdapter {

        //어드민 유저 생성
        @Bean
        InMemoryUserDetailsManager userDetailsManager(){
            User.UserBuilder commonUser
                    = User.withUsername("commonUser")
                    .password("{noop}common").roles("USER");

            User.UserBuilder havi=User.withUsername("havi").password("{noop}test")
                    .roles("USER","ADMIN");

            List<UserDetails> userDetails=new ArrayList<>();
            userDetails.add(commonUser.build());
            userDetails.add(havi.build());

            return new InMemoryUserDetailsManager(userDetails);
        }


        //cors 설정
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            CorsConfiguration corsConfiguration=new CorsConfiguration();
            corsConfiguration.addAllowedOrigin(CorsConfiguration.ALL);
            corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);
            corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
            UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**",corsConfiguration);

            http.httpBasic()
                    .and().authorizeRequests()
                    .anyRequest().permitAll()
                    .and().cors().configurationSource(source)
                    .and().csrf().disable();

        }

        //이벤트 bean 등록
        @Bean
        BoardEventHandler boardEventHandler(){
            return new BoardEventHandler();
        }
    }
}

