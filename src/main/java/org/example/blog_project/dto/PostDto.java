package org.example.blog_project.dto;

import lombok.Data;

@Data
public class PostDto {
    private String title;
    private String content;
    private String tags;
    private boolean publishStatus;
}
