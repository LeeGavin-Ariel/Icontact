package com.myapp.backend.domain.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminTeacherDto {

    private String userId;
    private int stateCode;
    private int accept;
    private Date createDate;
    private String  profileImg;
    private String  classCode ;
}
