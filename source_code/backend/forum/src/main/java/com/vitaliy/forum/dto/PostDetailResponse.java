package com.vitaliy.forum.dto;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailResponse {
    private Integer postId;
    private String title;
    private String content;
    private String authorName;
    private String categoryName;
    private Date postDate;
    private List<CommentResponse> comments;
}
