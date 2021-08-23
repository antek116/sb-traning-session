package com.sbbetting.training.security;

import com.sbbetting.training.security.handler.ApiAccessDeniedHandler;
import com.sbbetting.training.security.handler.RestAuthenticationEntryPoint;
import com.sbbetting.training.security.token.TokenFilter;
import com.sbbetting.training.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final TokenService tokenService;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    public SecurityConfiguration(TokenService tokenService,
                                 RestAuthenticationEntryPoint entryPoint) {
        this.tokenService = tokenService;
        this.restAuthenticationEntryPoint = entryPoint;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf().disable()
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers("/rest/user").authenticated()
                .antMatchers("/rest/user/**").authenticated()
                .antMatchers("/**").permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new ApiAccessDeniedHandler())
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .addFilterBefore(new TokenFilter(tokenService), UsernamePasswordAuthenticationFilter.class);
    }
}
