package com.sbbetting.training.security.token;

import com.sbbetting.training.database.model.User;
import com.sbbetting.training.exception.ServiceException;
import com.sbbetting.training.security.data.CustomAuthentication;
import com.sbbetting.training.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenFilter extends OncePerRequestFilter {

    private final Logger LOG = LoggerFactory.getLogger(TokenFilter.class);

    private final TokenService tokenService;

    public TokenFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        try {
            final String tokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            final String token = tokenService.extractBearerToken(tokenHeader);
            if (token != null && !tokenService.isTokenExpired(token)) {
                final User user = tokenService.getUser(token);
                if (user != null) {
                    LOG.trace("User with id {} found for token {}", user.getUserId(), token);
                    final CustomAuthentication authentication = new CustomAuthentication(user);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            LOG.warn("Exception caught during filter request, Reason : {}", e.getMessage());
        }

        chain.doFilter(request, response);
    }
}
