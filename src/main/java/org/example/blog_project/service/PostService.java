package org.example.blog_project.service;

import lombok.RequiredArgsConstructor;
import org.example.blog_project.domain.Post;
import org.example.blog_project.domain.User;
import org.example.blog_project.repository.PostRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    // 글 등록
    @Transactional
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    // 사용자 글 목록 보기
    @Transactional(readOnly = true)
    public List<Post> findAllPostsByUser(User user) {
        return postRepository.findAllByAuthor(user);
    }

    // 모든 글 목록 보기
    @Transactional(readOnly = true)
    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    // 게시글 상세 조회
    @Transactional(readOnly = true)
    public Post findPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }


}
