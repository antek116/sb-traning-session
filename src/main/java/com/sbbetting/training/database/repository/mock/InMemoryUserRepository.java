package com.sbbetting.training.database.repository.mock;

import com.sbbetting.training.database.model.User;
import com.sbbetting.training.database.model.UserDetails;
import com.sbbetting.training.database.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private final HashMap<String, User> users = new HashMap<>();

    public InMemoryUserRepository() {
        initializeUsers();
    }

    private void initializeUsers() {
        final UserDetails user1Details = new UserDetails.Builder().withCity("Szczecin").withName("Krzysztof").withLastName("Antczak").build();
        final User user1 = new User(UUID.randomUUID().toString(), "krzysztof.antczak@sb-betting.com", "Abc123", user1Details);
        users.put(user1.getUserId(), user1);
        final UserDetails user2Details = new UserDetails.Builder().withCity("Szczecin").withName("Jan").withLastName("Kowalski").build();
        final User user2 = new User(UUID.randomUUID().toString(), "jan.kowalski@sb-betting.com", "123Abc", user2Details);
        users.put(user2.getUserId(), user2);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return users.values()
                .stream()
                .filter(user -> Objects.equals(user.getEmail(), email))
                .findFirst();
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return users.values()
                .stream()
                .filter(user -> Objects.equals(user.getUserId(), userId))
                .findFirst();
    }
}
