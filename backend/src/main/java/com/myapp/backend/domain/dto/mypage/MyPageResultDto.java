package com.myapp.backend.domain.dto.mypage;

import com.myapp.backend.domain.entity.Kinder;
import com.myapp.backend.domain.entity.KinderClass;
import com.myapp.backend.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//마이페이지 정보 반환용 DTO
public class MyPageResultDto {
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
    private String userTel;

    public void userInfoSet(User u) {
        this.userId = u.getUserId();
        this.userName = u.getUserName();
        this.userTel = u.getUserTel();
        this.type = u.getType();
        this.accept = u.getAccept();
        this.profileImg = u.getProfileImg();
        this.stateCode = u.getStateCode();
        this.kidName = u.getKidName();
        this.userTel = u.getUserTel();
        this.classCode = u.getClassCode();
    }

    public void kinderClassInfoSet(KinderClass k) {
        this.classCode = k.getClassCode();
        this.className = k.getClassName();
        this.kinderCode = k.getKinderCode();
        this.kinderName = k.getKinder().getKinderName();
    }
}
