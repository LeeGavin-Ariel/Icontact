package com.myapp.backend.dao;

import com.myapp.backend.domain.dto.notice.MenuResultDto;
import com.myapp.backend.domain.dto.notice.NoticeParamDto;
import com.myapp.backend.domain.dto.notice.NoticeResultDto;
import com.myapp.backend.domain.dto.notice.ScheduleResultDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScheduleDao {
    public List<ScheduleResultDto> scheduleList(NoticeParamDto noticeParamDto);

    public ScheduleResultDto scheduleDetail(int id);
}
