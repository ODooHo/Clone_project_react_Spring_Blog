package com.dooho.board.global.config

import com.dooho.board.global.filter.JwtAuthenticationFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
open class WebSecurityConfig @Autowired constructor(private val jwtAuthenticationFilter: JwtAuthenticationFilter) {
    @Bean
    @Throws(Exception::class)
    open fun SecurityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity
            .cors(Customizer.withDefaults())
            .csrf { csrf: CsrfConfigurer<HttpSecurity> -> csrf.disable() }
            .httpBasic { httpBasic: HttpBasicConfigurer<HttpSecurity> -> httpBasic.disable() }
            .sessionManagement { sessionManagement: SessionManagementConfigurer<HttpSecurity?> ->
                sessionManagement.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            }
            .authorizeHttpRequests(Customizer { authorizeHttpRequests: AuthorizeHttpRequestsConfigurer<*>.AuthorizationManagerRequestMatcherRegistry ->
                authorizeHttpRequests.anyRequest().permitAll()
//                authorizeHttpRequests.requestMatchers("/", "/api/auth/**").permitAll()
//                    .requestMatchers(HttpMethod.GET, "/api/board/**").authenticated()
//                    .requestMatchers(HttpMethod.POST, "/api/board/**").authenticated()
//                    .requestMatchers(HttpMethod.POST, "/api/user/**").authenticated()
//                    .anyRequest().permitAll()
            })
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
        return httpSecurity.build()
    }
}