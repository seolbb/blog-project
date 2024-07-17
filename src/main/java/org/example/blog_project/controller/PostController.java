package org.example.blog_project.controller;

import lombok.RequiredArgsConstructor;
import org.example.blog_project.domain.Post;
import org.example.blog_project.domain.User;
import org.example.blog_project.dto.PostDto;
import org.example.blog_project.service.PostService;
import org.example.blog_project.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final UserService userService;

    // 블로그 메인
    @GetMapping("/main")
    public String main(){
        return "blog/main";
    }

    // 블로그 글쓰기폼 이동
    @GetMapping("/postform")
    public String postform(){
        return "blog/postform";
    }

    // 블로그 글쓰기
    @PostMapping("/post")
    public String createPost(@ModelAttribute PostDto postDto, Authentication authentication) {
        // 현재 로그인한 사용자 정보 가져오기
        String username = authentication.getName();
        User author = userService.findByUsername(username);

        // 새로운 Post 객체 생성 및 설정
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setTags(postDto.getTags());
        post.setContent(postDto.getContent());
        post.setPublishStatus(postDto.isPublishStatus());
        post.setAuthor(author);

        postService.createPost(post);

        return "redirect:/@" + username + "/posts";
    }

    // 사용자 블로그 홈 이동
    @GetMapping("/@{username}/posts")
    public String userPage() {
        return "blog/blog";
    }

    // 게시글 상세 조회
    @GetMapping("@{username}/posts/{id}")
    public String getPostById(@PathVariable Long id, Model model){
        Post postById = postService.findPostById(id);
        model.addAttribute("post", postById);
        return "blog/postdetail";
    }


}