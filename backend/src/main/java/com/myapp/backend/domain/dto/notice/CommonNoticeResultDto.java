package com.myapp.backend.domain.dto.notice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonNoticeResultDto {
    int pageCnt;

    NoticeResultDto notice;
    List<NoticeResultDto> noticeList;

    ScheduleResultDto schedule;
    List<ScheduleResultDto> scheduleList;

    MenuResultDto menu;
    List<MenuResultDto> menuList;
}
