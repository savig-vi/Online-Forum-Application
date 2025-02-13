package com.vitaliy.forum.dto;

import lombok.Data;

@Data
public class CommentRequestDTO {
    private int postId;
    private String content;
}
