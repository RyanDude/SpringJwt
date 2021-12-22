package com.example.exp.Config;

import com.example.exp.Filter.JWTAuthenticationFilter;
import com.example.exp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth)throws Exception{
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        // 只是生成token 所以全部放行
        http.cors()
                .and()
                .csrf().disable();
        http.authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()));
    }
}
/*
server:
  port: 8080
spring:
  #通用的数据源配置
  datasource:
    driverClassName: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/project?useSSL=false&useUnicode=true&characterEncoding=utf8
    username: root
    password: 123456
  jpa:
    #这个参数是在建表的时候，将默认的存储引擎切换为 InnoDB 用的
    database-platform: org.hibernate.dialect.MySQL5InnoDBDialect
    #配置在日志中打印出执行的 SQL 语句信息。
    show-sql: true
    hibernate:
      #配置指明在程序启动的时候要删除并且创建实体类对应的表
      ddl-auto: update
* */