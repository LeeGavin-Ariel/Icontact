package com.myapp.backend.domain.dto.notice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeParamDto {
    private int noticeType;
    private String userId;
    private int pageNum;

    private int limit;
    private int offset;

}
