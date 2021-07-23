package com.myapp.backend.domain.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResultDto {

    private String userId;
    private String userName;
    private int type;
    private int kidId;
    private String kidName;
    private int accept;
    private int stateCode;
    private String profileImg;
    private String kinderCode;
    private String kinderName;
    private String classCode;
    private String className;
}
