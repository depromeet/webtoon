package com.depromeet.webtoon.api.config

import com.depromeet.webtoon.api.common.filter.CustomAuthorizationFilter
import com.depromeet.webtoon.core.domain.account.repository.AccountRepository
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy

@Configuration
@EnableWebSecurity
class SecurityConfig(val accountRepository: AccountRepository): WebSecurityConfigurerAdapter() {


    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
        http.cors()
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.formLogin().disable()
        http.httpBasic().disable()
        http.addFilter(CustomAuthorizationFilter(authenticationManager(),accountRepository))
        http.authorizeRequests()
            .mvcMatchers("/api/v1/login").permitAll()
            .anyRequest().authenticated()
    }
}
