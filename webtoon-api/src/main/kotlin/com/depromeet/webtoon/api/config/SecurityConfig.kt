package com.depromeet.webtoon.api.config

import com.depromeet.webtoon.api.common.authexception.ToonietoonieAccessDeniedHandler
import com.depromeet.webtoon.api.common.authexception.ToonietoonieAuthenticationEntryPoint
import com.depromeet.webtoon.api.common.filter.CustomAuthorizationFilter
import com.depromeet.webtoon.core.domain.account.repository.AccountRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler

@Configuration
@EnableWebSecurity
class SecurityConfig(val accountRepository: AccountRepository): WebSecurityConfigurerAdapter() {


    @Bean
    fun toonietoonieAccessDeniedHandler(): AccessDeniedHandler{
        return ToonietoonieAccessDeniedHandler()
    }

    @Bean
    fun toonietoonieAuthenticationEntryPoint(): AuthenticationEntryPoint{
        return ToonietoonieAuthenticationEntryPoint()
    }

    //todo swagger 접속시 왜 exceptionHandling 에 걸리는지 확인.. 필터는 걸리는데 에러는 안떨어지네?
    override fun configure(web: WebSecurity) {
        web.ignoring().mvcMatchers("/favicon.ico")
        web.ignoring().antMatchers("/v2/api-docs",
            "/configuration/**",
            "/swagger*/**",
            "/webjars/**")
    }

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
        http.cors()
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.formLogin().disable()
        http.httpBasic().disable()
        http.addFilter(CustomAuthorizationFilter(authenticationManager(),accountRepository))
        http.headers().frameOptions().disable()
        http.authorizeRequests()
            .mvcMatchers("/api/v1/login").permitAll()
            .mvcMatchers("/h2-console").permitAll()
            .mvcMatchers("/h2-console/**").permitAll()
            .anyRequest().authenticated()
        http.exceptionHandling().authenticationEntryPoint(toonietoonieAuthenticationEntryPoint())
        http.exceptionHandling().accessDeniedHandler(toonietoonieAccessDeniedHandler())
    }
}
