package com.myapp.backend.controller;

import com.myapp.backend.domain.dto.notice.NoticeDto;
import com.myapp.backend.service.NoticeService;
import com.myapp.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
public class NoticeController {

    @Autowired
    NoticeService noticeService;

    // 사진 첨부 아직 안함 S3 써야함
    @PostMapping("notice")
    public ResponseEntity noticeInsert(NoticeDto noticeDto, MultipartHttpServletRequest request){
        int result = 0;
        int noticeType = noticeDto.getNoticeType();

        if(noticeType == 1){ // 일반 공지
            result = noticeService.noticeInsert(noticeDto, request);
        } else if (noticeType == 2){ // 일정
            result = noticeService.scheduleInsert(noticeDto, request);
        } else { // 식단
            result = noticeService.menuInsert(noticeDto, request);
        }

        if (result == 1) {
            return new ResponseEntity<Integer>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<Integer>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("notice/{id}")
    public ResponseEntity noticeUpdate(NoticeDto noticeDto, MultipartHttpServletRequest request){
        int result = 0;
        int noticeType = noticeDto.getNoticeType();

        if(noticeType == 1){ // 일반 공지
            result = noticeService.noticeUpdate(noticeDto, request);
        } else if (noticeType == 2){ // 일정
            result = noticeService.scheduleUpdate(noticeDto, request);
        } else { // 식단
            result = noticeService.menuUpdate(noticeDto, request);
        }

        if (result == 1) {
            return new ResponseEntity<Integer>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<Integer>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/notice")
    public ResponseEntity noticeDelete(@RequestParam(required = true) final int noticeType,
                                       @RequestParam(required = true) final int id){
        int result = 0;

        if(noticeType == 1){ // 일반 공지
            result = noticeService.noticeDelete(id);
        } else if (noticeType == 2){ // 일정
            result = noticeService.scheduleDelete(id);
        } else { // 식단
            result = noticeService.menuDelete(id);
        }

        if (result == 1) {
            return new ResponseEntity<Integer>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<Integer>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
