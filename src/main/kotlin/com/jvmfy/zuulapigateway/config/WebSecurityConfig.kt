package com.jvmfy.zuulapigateway.config

import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy

@Configuration
@EnableWebSecurity
class WebSecurityConfig(val env: Environment) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
        http.headers().frameOptions().disable()
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, this.env.getProperty("api.login.url")).permitAll()
                .antMatchers(HttpMethod.POST, this.env.getProperty("api.registration.url")).permitAll()
                .antMatchers(this.env.getProperty("api.h2-console.url")).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(AuthorizationFilter(this.authenticationManager(), this.env))
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //avoid caching JWT token, in every REST call, jwt has to be included in the header
    }
}