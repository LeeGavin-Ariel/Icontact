package com.myapp.backend.service;

import com.myapp.backend.domain.dto.notice.NoticeDto;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;


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
}
