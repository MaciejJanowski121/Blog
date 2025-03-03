package org.example.blog.repository;

import org.example.blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<org.example.blog.model.Post, Long> {

 Post getPostById(Long id);
 List<Post> findAllByOrderByIdDesc();
}
