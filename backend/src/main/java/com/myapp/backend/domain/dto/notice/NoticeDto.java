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

    // 공지, 일정 공통
    private String title;
    private String content;

    // 식단
    private String amSnack;
    private String lunch;
    private String pmSnack;

}
