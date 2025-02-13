package com.vitaliy.forum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserUpdateActiveDTO {
    private int userId;
    private boolean active;
}
