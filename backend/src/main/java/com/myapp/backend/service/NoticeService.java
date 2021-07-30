package com.myapp.backend.service;

import com.myapp.backend.domain.dto.notice.MenuResultDto;
import com.myapp.backend.domain.dto.notice.NoticeDto;
import com.myapp.backend.domain.dto.notice.NoticeResultDto;
import com.myapp.backend.domain.dto.notice.ScheduleResultDto;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;


public interface NoticeService {
    public int noticeInsert(NoticeDto noticeDto, MultipartHttpServletRequest request);

    public int scheduleInsert(NoticeDto noticeDto, MultipartHttpServletRequest request);

    public int menuInsert(NoticeDto noticeDto, MultipartHttpServletRequest request);

    public int noticeUpdate(NoticeDto noticeDto, MultipartHttpServletRequest request);

    public int scheduleUpdate(NoticeDto noticeDto, MultipartHttpServletRequest request);

    public int menuUpdate(NoticeDto noticeDto, MultipartHttpServletRequest request);

    @Transactional
    public int noticeDelete(int id);

    @Transactional
    public int scheduleDelete(int id);

    @Transactional
    public int menuDelete(int id);

    public List<NoticeResultDto> noticeList(String userId);

    public List<ScheduleResultDto> scheduleList(String userId);

    public List<MenuResultDto> menuList(String userId);
}
