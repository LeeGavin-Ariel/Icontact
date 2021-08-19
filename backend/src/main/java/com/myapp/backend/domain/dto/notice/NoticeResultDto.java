package com.myapp.backend.domain.dto.notice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeResultDto {
    private String userId;
    private String userName;
    private int noticeId;
    private String title;
    private String content;
    private String noticeImgUrl;
    private String createDate;
}
