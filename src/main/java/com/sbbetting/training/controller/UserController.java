package com.sbbetting.training.controller;

import com.sbbetting.training.database.model.User;
import com.sbbetting.training.model.dto.response.UserDTO;
import com.sbbetting.training.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO getUserData(@AuthenticationPrincipal User user) {
        LOG.trace("Get user data request received for user with id {}", user.getUserId());
        return service.getUserData(user);
    }
}
