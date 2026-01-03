package com.pranjal.user_service.repository;

import com.pranjal.user_service.entity.User;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    @NullMarked
    Optional<User> findById(String id);
}
