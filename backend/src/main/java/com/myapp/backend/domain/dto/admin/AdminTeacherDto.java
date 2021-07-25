package com.myapp.backend.domain.dto.admin;

import com.myapp.backend.domain.entity.Teacher;
import com.myapp.backend.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminTeacherDto extends AdminUserDto{

    private String userId;
    private int stateCode;
    private int accept;
    private Date createDate;
    private String  profileImg;
    private String  classCode ;
//    private User user;

    public AdminTeacherDto(Teacher t) {
        super(t.getUser());
        this.userId = t.getUserId();
        this.stateCode = t.getStateCode();
        this.accept = t.getAccept();
        this.createDate = t.getCreateDate();
        this.profileImg = t.getProfileImg();
        this.classCode = t.getClassCode();
    }

}
