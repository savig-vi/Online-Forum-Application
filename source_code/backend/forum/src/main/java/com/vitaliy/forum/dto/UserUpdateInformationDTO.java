package com.vitaliy.forum.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@NoArgsConstructor
public class UserUpdateInformationDTO {

    private int userId;
    private String userName;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String address;
    private String newPasswordHash;
}
