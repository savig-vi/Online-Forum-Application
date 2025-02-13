package com.vitaliy.forum.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostResponseDTO {
    private String message;
    private Object data;
    
    public PostResponseDTO(String message) {
        this.message = message;
    }
}
