package com.vitaliy.forum.dto;

import java.util.Date;

import com.vitaliy.forum.entity.Post;

import lombok.Getter;

// Cái này dùng để tải thông tin bài viết trong posts.html
@Getter
public class PostResponseDTO {
    private int postId;
    private String title;
    private String contentPreview;
    private String authorName;
    private Date postDate;
    
    public PostResponseDTO(Post post) {
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.contentPreview = post.getContent().length() > 25 
                ? post.getContent().substring(0, 25) + "..."
                : post.getContent();
        this.authorName = post.getAuthor().getFullName();
        this.postDate = post.getPostDate();
    }
}
