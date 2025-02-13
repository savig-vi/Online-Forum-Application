package com.vitaliy.forum.dto.post;

import lombok.Data;

@Data
public class CreatePostRequestDTO {
    private String title;
    private String content;
    private int categoryId;
}
