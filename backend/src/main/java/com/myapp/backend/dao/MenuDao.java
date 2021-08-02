package com.myapp.backend.dao;

import com.myapp.backend.domain.dto.notice.MenuResultDto;
import com.myapp.backend.domain.dto.notice.NoticeParamDto;
import com.myapp.backend.domain.dto.notice.NoticeResultDto;
import com.myapp.backend.domain.dto.notice.ScheduleResultDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuDao {
    public List<MenuResultDto> menuList(NoticeParamDto noticeParamDto);

    public MenuResultDto menuDetail(int id);
}
