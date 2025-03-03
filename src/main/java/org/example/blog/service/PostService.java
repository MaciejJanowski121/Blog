package org.example.blog.service;

import org.example.blog.model.Post;
import org.example.blog.model.PostDTO;
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


    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }
    public Post savePost(Post post) {
        return postRepository.save(post);
    }
    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }


    public PostDTO createPost (PostDTO postDTO) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Post post = new Post();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setAuthor(username);
        post.setUser(user);
        postRepository.save(post);

        return new PostDTO(post.getId(),post.getTitle(),post.getContent(),post.getAuthor());

    }
    public PostDTO updatePost(Long id, PostDTO updatedPostDTO) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));

        // Pobierz zalogowanego użytkownika
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        // Sprawdź, czy to właściciel posta
        if (!post.getAuthor().equals(username)) {
            throw new RuntimeException("Unauthorized: You can only edit your own posts");
        }

        // Aktualizuj dane posta
        post.setTitle(updatedPostDTO.getTitle());
        post.setContent(updatedPostDTO.getContent());

        postRepository.save(post);

        return new PostDTO(post.getId(), post.getTitle(), post.getContent(), post.getAuthor());
    }

    public List<PostDTO> allPosts () {
        return postRepository.findAllByOrderByIdDesc().stream().map(post -> new PostDTO(post.getId(),post.getTitle(),post.getContent(),post.getAuthor())).collect(java.util.stream.Collectors.toList());
    }


    }

