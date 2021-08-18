package com.myapp.backend.dao;

import com.myapp.backend.domain.dto.notice.NoticeParamDto;
import com.myapp.backend.domain.dto.notice.NoticeResultDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeDao {
    public List<NoticeResultDto> noticeList(NoticeParamDto noticeParamDto);

    public NoticeResultDto noticeDetail(int id);

    public int totalNoticeList(String userId);
}
