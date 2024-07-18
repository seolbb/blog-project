package org.example.blog_project.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.blog_project.domain.Post;
import org.example.blog_project.domain.User;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private boolean publishStatus;
    private LocalDateTime createdAt;
    private int views;
    private String authorUsername;

    // Entity => DTO
    public PostDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.publishStatus = post.isPublishStatus();
        this.createdAt = post.getCreatedAt();
        this.views = post.getViews();
        this.authorUsername = post.getAuthor().getUsername();
    }
}
