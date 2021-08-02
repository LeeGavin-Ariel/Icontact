package com.myapp.backend.domain.dto.notice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResultDto {
    private String userId;
    private String userName;
    private int scheduleId;
    private String title;
    private String content;
    private String scheduleImgUrl;
    private String createDate;
}
