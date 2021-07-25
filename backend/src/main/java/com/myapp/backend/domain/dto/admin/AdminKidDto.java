package com.myapp.backend.domain.dto.admin;

import com.myapp.backend.domain.entity.Kid;
import com.myapp.backend.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminKidDto extends AdminUserDto {

    private int kidId;          //아이 id
    private String kidName;     //아이 이름
    private int postCnt;        //작성 게시글 수
    private int followerCnt;    //나를 팔로우하는 사람 수
    private int followCnt;      //내가 팔로우하는 사람 수
    private String statusMsg;   //상태 메시지
    private int accept;         //승인상태
    private Date createDate;    //생성 날짜
    private String profileImg;  //프로필 이미지 경로+이름
    private String classCode;   //반 코드

    //외래키로 설정 객체변수로 받을지 고민하기!
    private String userId;      //학부모 Id
//    private User user;

    public AdminKidDto(Kid k) {
        super(k.getUser());
        this.kidId = k.getKidId();
        this.kidName = k.getKidName();
        this.postCnt = k.getPostCnt();
        this.followerCnt = k.getFollowerCnt();
        this.followCnt = k.getFollowCnt();
        this.statusMsg = k.getStatusMsg();
        this.accept = k.getAccept();
        this.createDate = k.getCreateDate();
        this.profileImg = k.getProfileImg();
        this.classCode = k.getClassCode();
        this.userId = k.getUserId();
//        this.user = k.getUser();

    }

}
