package com.vitaliy.forum.dto.post;

import lombok.Data;

@Data
public class UpdatePostRequestDTO {
    private String title;
    private String content;
}