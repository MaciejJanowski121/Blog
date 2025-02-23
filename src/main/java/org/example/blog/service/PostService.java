package org.example.blog.service;

import org.example.blog.model.Post;
import org.example.blog.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
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
        return postRepository.save(post);
    }
    public Post updatePost(Long id, Post updatedPost) {
        Post post = postRepository.getPostById(id);
        if (post != null) {
            post.setTitle(updatedPost.getTitle());
            post.setContent(updatedPost.getContent());

        }

        return postRepository.save(post);
    }
}
