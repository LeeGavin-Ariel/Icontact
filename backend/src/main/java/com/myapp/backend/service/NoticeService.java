package com.myapp.backend.service;

import com.myapp.backend.domain.dto.notice.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;


public interface NoticeService {
    public int noticeInsert(NoticeDto noticeDto);

    public int scheduleInsert(NoticeDto noticeDto);

    public int menuInsert(NoticeDto noticeDto);

    public int noticeUpdate(NoticeDto noticeDto);

    public int scheduleUpdate(NoticeDto noticeDto);

    public int menuUpdate(NoticeDto noticeDto);

    @Transactional
    public int noticeDelete(int id);

    @Transactional
    public int scheduleDelete(int id);

    @Transactional
    public int menuDelete(int id);

    public List<NoticeResultDto> noticeList(NoticeParamDto noticeParamDto);

    public List<ScheduleResultDto> scheduleList(NoticeParamDto noticeParamDto);

    public List<MenuResultDto> menuList(NoticeParamDto noticeParamDto);

    public NoticeResultDto noticeDetail(int id);

    public ScheduleResultDto scheduleDetail(int id);

    public MenuResultDto menuDetail(int id);

    public int totalNoticeList(String userId);

    public int totalScheduleList(String userId);

    public int totalMenuList(String userId);
}
