package com.sbbetting.training.database.repository.mock;

import com.sbbetting.training.database.model.RefreshToken;
import com.sbbetting.training.database.repository.RefreshTokenRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryRefreshTokenRepository implements RefreshTokenRepository {

    private final Map<String, RefreshToken> container = new HashMap<>();

    @Override
    public Optional<RefreshToken> findByUserId(String userId) {
        return Optional.ofNullable(container.get(userId));
    }

    @Override
    public void save(RefreshToken refreshToken) {
        container.put(refreshToken.getToken(), refreshToken);
    }
}
