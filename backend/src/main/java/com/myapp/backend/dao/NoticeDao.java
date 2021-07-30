package com.myapp.backend.dao;

import com.myapp.backend.domain.dto.notice.MenuResultDto;
import com.myapp.backend.domain.dto.notice.NoticeResultDto;
import com.myapp.backend.domain.dto.notice.ScheduleResultDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeDao {
    public List<NoticeResultDto> noticeList(String userId);

    public List<ScheduleResultDto> scheduleList(String userId);

    public List<MenuResultDto> menuList(String userId);
}
