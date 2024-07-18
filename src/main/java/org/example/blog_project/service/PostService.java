package org.example.blog_project.service;

import lombok.RequiredArgsConstructor;
import org.example.blog_project.domain.Post;
import org.example.blog_project.domain.User;
import org.example.blog_project.dto.PostDto;
import org.example.blog_project.repository.PostRepository;
import org.example.blog_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 글 등록
    @Transactional
    public Post createPost(PostDto postDto, String username) {
        User author = userRepository.findByUsername(username);
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setPublishStatus(postDto.isPublishStatus());
        post.setAuthor(author);
        return postRepository.save(post);
    }

    // 사용자 글 목록 보기
    @Transactional(readOnly = true)
    public List<PostDto> findAllPostsByUser(User user) {
        List<Post> posts = postRepository.findAllByAuthor(user);
        return posts.stream()
                .map(post -> new PostDto(post)) // Entity를 DTO로 변환
                .collect(Collectors.toList());
    }

    // 모든 글 목록 보기
    public List<PostDto> findAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(PostDto::new) // Entity를 DTO로 변환
                .collect(Collectors.toList());
    }

    // 게시글 상세 조회
    @Transactional(readOnly = true)
    public PostDto findPostById(Long id) {
        Post post = postRepository.findById(id).orElse(null);
        if (post != null) {
            return new PostDto(post); // Entity를 DTO로 변환
        }
        return null;
    }

    // 조회수 증가
    @Transactional
    public void increaseViews(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null) {
            post.setViews(post.getViews() + 1);
            postRepository.save(post);
        } else {
            throw new IllegalArgumentException("게시물이 존재하지 않습니다.");
        }
    }


    // 게시글 삭제
    @Transactional
    public void deletePost(Long postId, String username) {
        Post post = postRepository.findById(postId).orElse(null);

        // 작성자 확인 로직
        if (!(post.getAuthor().getUsername()).equals(username)) {
            throw new SecurityException("삭제 권한이 없습니다.");
        }

        postRepository.delete(post);
    }


}
