package com.sbbetting.training.service;

import com.sbbetting.training.database.model.User;
import com.sbbetting.training.database.repository.UserRepository;
import com.sbbetting.training.exception.ServiceException;
import com.sbbetting.training.model.dto.response.TokenDTO;
import com.sbbetting.training.security.token.TokenType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class AuthorizationService {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Autowired
    public AuthorizationService(TokenService tokenService,
                                UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    /*
      We should return tokenDTO for user from credentials.

      -> find user and validate password,
      -> create token and refresh token for this user.
      -> return token and refresh token in token DTO.
     */
    public TokenDTO createToken(String login, String password) {
        final Optional<User> user = userRepository.findByEmail(login);

        if (!user.isPresent()) {
            throw new ServiceException("User not found", HttpStatus.UNAUTHORIZED);
        }

        final boolean isPasswordValid = Objects.equals(user.get().getPassword(), password);
        if (!isPasswordValid) {
            throw new ServiceException("Password does not match!", HttpStatus.UNAUTHORIZED);
        }

        final String token = tokenService.createToken(user.get().getUserId());
        return new TokenDTO(token, TokenType.BEARER, "");
    }

    /*
       We should refresh token for valid refresh token and user from token.
       -> find user with is assigned to token
       -> find refresh token for this user from database
       -> validate if refresh token from database equals refresh token from method argument
       -> check if the refresh token do not expired.
       -> create new access token and refresh token.
       -> return token and refresh token in token DTO.
     */
    public TokenDTO refreshToken(String token,
                                 String refreshToken) {
        return null;
    }
}
