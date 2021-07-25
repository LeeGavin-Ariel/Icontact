package com.myapp.backend.domain.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserDto implements AdminUser {
    //유저 부분
    private String userId;
    private String password;
    private String userName;
    private String userTel;
    private int type;
}
