package org.example.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<org.example.blog.model.Post, Long> {
}
