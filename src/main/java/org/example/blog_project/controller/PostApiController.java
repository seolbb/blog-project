package org.example.blog_project.controller;

import lombok.RequiredArgsConstructor;
import org.example.blog_project.domain.Post;
import org.example.blog_project.domain.User;
import org.example.blog_project.service.PostService;
import org.example.blog_project.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostApiController {
    private final PostService postService;

    // 게시글 목록 조회
    @GetMapping
    public ResponseEntity<List<Post>> findAllPosts(){
        List<Post> posts = postService.findAllPosts();
        return ResponseEntity.ok(posts);
    }


}

