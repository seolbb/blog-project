package org.example.blog_project.dto;

import lombok.Data;
import org.example.blog_project.domain.Post;

import java.time.LocalDateTime;

@Data
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private boolean publishStatus;
    private LocalDateTime createdAt;
    private String authorUsername;

    // Entity => DTO
    public PostDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.publishStatus = post.isPublishStatus();
        this.createdAt = post.getCreatedAt();
        this.authorUsername = post.getAuthor().getUsername();
    }
}
