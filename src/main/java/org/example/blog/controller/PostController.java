package org.example.blog.controller;

import org.example.blog.model.Post;
import org.example.blog.model.PostDTO;
import org.example.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    @GetMapping
    public List<PostDTO> getAllPosts() {
        return postService.allPosts();
    }


    @GetMapping("/{id}")
    public PostDTO getPostById(@PathVariable Long id) {
        return new PostDTO(
                postService.getPostById(id).getId(),
                postService.getPostById(id).getTitle(),
                postService.getPostById(id).getContent(),
                postService.getPostById(id).getAuthor()
        );
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostDTO createPost(@RequestBody PostDTO postDTO) {
        return postService.createPost(postDTO);
    }


    @PutMapping("/{id}")
    public PostDTO updatePost(@PathVariable Long id, @RequestBody PostDTO updatedPost) {
        return postService.updatePost(id, updatedPost);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePostById(@PathVariable Long id) {
        postService.deletePostById(id);
    }
}