package com.myapp.backend.domain.dto.join;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JoinDto {

    private String userId;
    private String password;
    private String userName;
    private String userTel;
    private int type;

    private String classCode;

    //parents
    private String kidName;


}
