package com.sbbetting.training.controller;

import com.sbbetting.training.model.dto.request.LoginDTO;
import com.sbbetting.training.model.dto.request.RefreshTokenDTO;
import com.sbbetting.training.model.dto.response.TokenDTO;
import com.sbbetting.training.service.AuthorizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/authorization")
public class AuthorizationController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorizationController.class);

    private final AuthorizationService service;

    @Autowired
    public AuthorizationController(AuthorizationService service) {
        this.service = service;
    }

    @PostMapping("/token")
    public TokenDTO createToken(@RequestBody LoginDTO loginDTO) {
        LOG.info("Get token request received for customer with login {}", loginDTO.getLogin());
        return service.createToken(loginDTO.getLogin(), loginDTO.getPassword());
    }

    @PostMapping("/token/refresh")
    public TokenDTO refreshToken(@RequestBody RefreshTokenDTO refreshTokenDTO,
                                 @RequestHeader(value = HttpHeaders.AUTHORIZATION, defaultValue = "") String token) {
        LOG.info("Refresh token request received for token {}", token);
        return service.refreshToken(token, refreshTokenDTO.getRefreshToken());
    }
}
