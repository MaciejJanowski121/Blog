package org.example.blog.repository;

import org.example.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<org.example.blog.model.User, Long> {
    Optional<User> findByUsername(String username);
}
