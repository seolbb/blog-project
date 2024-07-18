package org.example.blog_project.repository;

import org.example.blog_project.domain.Post;
import org.example.blog_project.domain.User;
import org.example.blog_project.dto.PostDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByAuthor(User author);
}
