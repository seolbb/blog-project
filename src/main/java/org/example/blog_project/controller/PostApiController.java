package org.example.blog_project.controller;

import lombok.RequiredArgsConstructor;
import org.example.blog_project.domain.Post;
import org.example.blog_project.domain.User;
import org.example.blog_project.dto.PostDto;
import org.example.blog_project.service.PostService;
import org.example.blog_project.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostApiController {
    private final PostService postService;
    private final UserService userService;

    // 게시글 목록 조회
    @GetMapping
    public ResponseEntity<List<PostDto>> findAllPosts(){
        List<PostDto> posts = postService.findAllPosts();
        return ResponseEntity.ok(posts);
    }

    // 임시저장 글 목록 조회
    @GetMapping("/drafts")
    public ResponseEntity<List<PostDto>> findAllUnpublishedPosts(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        String username = userDetails.getUsername();
        List<PostDto> unpublishedPosts = postService.findUnpublishedPostsByUser(user);
        return ResponseEntity.ok(unpublishedPosts);
    }

    // 게시물 삭제
    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        postService.deletePost(postId, username);
    }


}

