package com.myapp.backend.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Kid")
public class Kid {

    @Id
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
}
