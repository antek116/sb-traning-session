package com.sbbetting.training.database.repository;

import com.sbbetting.training.database.model.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);

    Optional<User> findByUserId(String userId);
}
