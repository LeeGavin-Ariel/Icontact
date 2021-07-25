package com.myapp.backend.domain.dto.admin;

import com.myapp.backend.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserDto {
    //유저 부분
    private String userId;
    private String password;
    private String userName;
    private String userTel;
    private int type;

    public AdminUserDto(User u) {
        this.userId = u.getUserId();
        this.password = u.getPassword();
        this.userName = u.getUserName();
        this.userTel = u.getUserTel();
        this.type = u.getType();
    }
}
