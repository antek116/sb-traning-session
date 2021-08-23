package com.sbbetting.training.database.repository;

import com.sbbetting.training.database.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository {

    Optional<RefreshToken> findByUserId(String userId);

    void save(RefreshToken refreshToken);
}
