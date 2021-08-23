package com.sbbetting.training.service;

import com.sbbetting.training.database.model.RefreshToken;
import com.sbbetting.training.database.model.User;
import com.sbbetting.training.database.repository.RefreshTokenRepository;
import com.sbbetting.training.database.repository.UserRepository;
import com.sbbetting.training.error.TokenError;
import com.sbbetting.training.exception.ServiceException;
import com.sbbetting.training.security.token.TokenParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TokenService {

    private static final Logger LOG = LoggerFactory.getLogger(TokenService.class);

    private final TokenParser tokenParser;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Autowired
    public TokenService(TokenParser tokenParser,
                        UserRepository userRepository,
                        RefreshTokenRepository refreshTokenRepository) {
        this.tokenParser = tokenParser;
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public String createToken(String userId) {
        LOG.trace("Create token for user with id {}", userId);
        return tokenParser.createToken(userId, 15, TimeUnit.MINUTES);
    }

    public boolean isTokenExpired(String token) {
        LOG.trace("Checking if token expired, token : {}", token);
        final Date tokenExpirationDate = tokenParser.getExpirationDate(token);
        return new Date(System.currentTimeMillis()).after(tokenExpirationDate);
    }

    public RefreshToken createRefreshToken(String userId) {
        LOG.trace("Creating refresh token for user with id {}", userId);
        final String token = UUID.randomUUID().toString();
        final Date expireDate = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7));

        final Optional<RefreshToken> actualRefreshToken = refreshTokenRepository.findByUserId(userId);
        if (actualRefreshToken.isPresent()) {
            final RefreshToken refreshToken = actualRefreshToken.get();
            refreshToken.setToken(token);
            refreshToken.setExpirationDate(expireDate);
            refreshTokenRepository.save(refreshToken);
            return refreshToken;
        }

        final RefreshToken refreshToken = new RefreshToken.Builder()
                .withUserId(userId)
                .withRefreshToken(token)
                .withExpirationDate(expireDate)
                .build();

        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken getRefreshToken(String userId) {
        LOG.trace("Getting refresh token for user with id {}", userId);
        final Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUserId(userId);
        if (!refreshToken.isPresent()) {
            throw new ServiceException(TokenError.INVALID_REFRESH_TOKEN_ERROR);
        }

        return refreshToken.get();
    }

    public String extractBearerToken(String token) {
        if (token == null || token.isEmpty()) {
            throw new ServiceException(TokenError.ACCESS_TOKEN_NOT_FOUND_ERROR);
        }

        if (!token.startsWith("Bearer ")) {
            throw new ServiceException(TokenError.INVALID_TOKEN_ERROR);
        }

        return token.substring(7);
    }

    public User getUser(String token) {
        LOG.trace("Getting user from token : {}", token);
        final String userId = tokenParser.getUserId(token);
        final Optional<User> user = userRepository.findByUserId(userId);

        if (!user.isPresent()) {
            LOG.trace("User from token not found.");
            throw new ServiceException(TokenError.INVALID_TOKEN_ERROR);
        }

        return user.get();
    }
}
