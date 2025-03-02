package org.example.blog.service;

import org.example.blog.model.Post;
import org.example.blog.model.User;
import org.example.blog.repository.PostRepository;
import org.example.blog.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }
    public Post savePost(Post post) {
        return postRepository.save(post);
    }
    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }
    public Post createPost(Post post) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        post.setAuthor(username);
        post.setUser(user);
        return postRepository.save(post);
    }
    public Post updatePost(Long id, Post updatedPost) {
        Post post = postRepository.findById(id).orElse(null);
        if (post != null) {
            post.setTitle(updatedPost.getTitle());
            post.setContent(updatedPost.getContent());
        }
        return postRepository.save(post);
    }


    }

