package com.myapp.backend.domain.dto.notice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDto {
    private int noticeType;
    private int id;
    private String userId;
    private String classCode;
//    private int pageNum;

    // 공지, 일정 공통
    private String title;
    private String content;

    // 공지
    private String noticeImgUrl;

    // 일정
    private String scheduleImgUrl;

    // 식단
    private String amSnack;
    private String amSnackImgUrl;
    private String lunch;
    private String lunchImgUrl;
    private String pmSnack;
    private String pmSnackImgUrl;

}
