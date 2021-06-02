package com.depromeet.webtoon.admin.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.BeanIds
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var jwtFilter: JwtFilter

    @Autowired
    lateinit var customUserDetailService: CustomUserDetailsService

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    override fun authenticationManager(): AuthenticationManager {
        return super.authenticationManager()
    }

    override fun configure(
        authenticationManagerBuilder: AuthenticationManagerBuilder
    ) {
        authenticationManagerBuilder // 인증 객체 생성 제공
            // 스프링 시큐리티 인증용.
            // userRepository를 통해 영속성으로 저장된 인증정보를 검색한 후 존재하지 않는다면 exception 반환
            // 있다면 UserDetails 객체 반환
            .userDetailsService<UserDetailsService>(customUserDetailService)
            .passwordEncoder(passwordEncoder()) // 패스워드 암호화 구현체
    }

    override fun configure(http: HttpSecurity) {
        http.cors()
            .and()

            .csrf()
            .disable()

            .sessionManagement()
            .disable()

            .authorizeRequests()
            .antMatchers("/**").permitAll()
//            .antMatchers("/api/**").authenticated()
            .antMatchers("/api/auth/**").permitAll()

            .and()

            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
    }
}
