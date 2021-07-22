package com.myapp.backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoinDto {

    private String userId;
    private String password;
    private String userName;
    private String userTel;
    private int type;

    private String classCode;

    //teacher

    //parents
    private String kidName;


}
