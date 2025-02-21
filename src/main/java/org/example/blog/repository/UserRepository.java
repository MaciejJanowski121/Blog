package org.example.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<org.example.blog.model.User, Long> {
}
