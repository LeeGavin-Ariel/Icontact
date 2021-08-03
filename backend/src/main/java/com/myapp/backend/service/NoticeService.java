package com.myapp.backend.service;

import com.myapp.backend.domain.dto.notice.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;


public interface NoticeService {
    public int noticeInsert(NoticeDto noticeDto, MultipartFile request);

    public int scheduleInsert(NoticeDto noticeDto, MultipartHttpServletRequest request);

    public int menuInsert(NoticeDto noticeDto, MultipartHttpServletRequest request);

    public int noticeUpdate(NoticeDto noticeDto, MultipartFile request);

    public int scheduleUpdate(NoticeDto noticeDto, MultipartHttpServletRequest request);

    public int menuUpdate(NoticeDto noticeDto, MultipartHttpServletRequest request);

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
