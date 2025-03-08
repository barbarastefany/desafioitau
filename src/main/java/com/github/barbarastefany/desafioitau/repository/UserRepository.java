package com.github.barbarastefany.desafioitau.repository;

import com.github.barbarastefany.desafioitau.model.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository {

    private final Map<Long, UserEntity> users = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public UserEntity save(UserEntity user) {
        user.setId(idGenerator.getAndIncrement());
        users.put(user.getId(), user);
        return user;
    }

    public Optional<UserEntity> findById(Long id) {
        return Optional.ofNullable(users.get(id));
    }
}
