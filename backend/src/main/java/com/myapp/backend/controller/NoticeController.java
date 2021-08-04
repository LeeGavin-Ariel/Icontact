package com.myapp.backend.controller;

import com.myapp.backend.domain.dto.notice.*;
import com.myapp.backend.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class NoticeController {

    @Autowired
    NoticeService noticeService;

    // 사진 첨부 아직 안함 S3 써야함

    // 공지글 등록
    @PostMapping("notice")
    public ResponseEntity noticeInsert(@ModelAttribute NoticeDto noticeDto, @RequestParam(required = false) MultipartFile img) {
        int result = 0;
        int noticeType = noticeDto.getNoticeType();

        if (noticeType == 1) { // 일반 공지
            result = noticeService.noticeInsert(noticeDto, img);
        } else if (noticeType == 2) { // 일정
            result = noticeService.scheduleInsert(noticeDto, img);
        } else { // 식단
//            result = noticeService.menuInsert(noticeDto, img);
        }

        if (result == 1) {
            return new ResponseEntity<Integer>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<Integer>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 공지글 수정
    @PostMapping("notice/{noticeType}")
    public ResponseEntity noticeUpdate(@ModelAttribute NoticeDto noticeDto, @RequestParam(required = false) MultipartFile img) {
        int result = 0;
        int noticeType = noticeDto.getNoticeType();

        System.out.println("noticeDTO:"+noticeDto);
        System.out.println("img:"+img);

        if (noticeType == 1) { // 일반 공지
            result = noticeService.noticeUpdate(noticeDto, img);
        } else if (noticeType == 2) { // 일정
            result = noticeService.scheduleUpdate(noticeDto, img);
        } else { // 식단
//            result = noticeService.menuUpdate(noticeDto, request);
        }

        if (result == 1) {
            return new ResponseEntity<Integer>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<Integer>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 공지글 삭제
    @DeleteMapping("/notice")
    public ResponseEntity noticeDelete(@RequestParam(required = true) final int noticeType,
                                       @RequestParam(required = true) final int id) {
        System.out.println("id:"+id);
        int result = 0;

        if (noticeType == 1) { // 일반 공지
            result = noticeService.noticeDelete(id);
        } else if (noticeType == 2) { // 일정
            result = noticeService.scheduleDelete(id);
        } else { // 식단
//            result = noticeService.menuDelete(id);
        }

        if (result == 1) {
            return new ResponseEntity<Integer>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<Integer>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 공지글 목록 조회
    @GetMapping("/notice")
    public ResponseEntity<CommonNoticeResultDto> noticeList(NoticeParamDto noticeParamDto) {
        System.out.println(noticeParamDto);

        CommonNoticeResultDto commonNoticeResultDto = new CommonNoticeResultDto();

        int noticeType = noticeParamDto.getNoticeType();
        int limit = 2;
        noticeParamDto.setLimit(limit);
        noticeParamDto.setOffset((noticeParamDto.getPageNum() - 1) * limit);

        int total = 0;

        if (noticeType == 1) { // 일반 공지

            total = noticeService.totalNoticeList(noticeParamDto.getUserId());
            commonNoticeResultDto.setPageCnt(calcPageCnt(total, limit));

            List<NoticeResultDto> noticeList = noticeService.noticeList(noticeParamDto);
            if (noticeList != null) {
                commonNoticeResultDto.setNoticeList(noticeList);
                for (NoticeResultDto n:noticeList
                     ) {
                    System.out.println(n);
                }
                return new ResponseEntity<CommonNoticeResultDto>(commonNoticeResultDto, HttpStatus.OK);
            }
        } else if (noticeType == 2) { // 일정

            total = noticeService.totalScheduleList(noticeParamDto.getUserId());
            commonNoticeResultDto.setPageCnt(calcPageCnt(total, limit));

            List<ScheduleResultDto> scheduleList = noticeService.scheduleList(noticeParamDto);
            if (scheduleList != null) {
                commonNoticeResultDto.setScheduleList(scheduleList);
                return new ResponseEntity<CommonNoticeResultDto>(commonNoticeResultDto, HttpStatus.OK);
            }
        } else { // 식단

            total = noticeService.totalMenuList(noticeParamDto.getUserId());
            commonNoticeResultDto.setPageCnt(calcPageCnt(total, limit));

            List<MenuResultDto> menuList = noticeService.menuList(noticeParamDto);
            if (menuList != null) {
                commonNoticeResultDto.setMenuList(menuList);
                return new ResponseEntity<CommonNoticeResultDto>(commonNoticeResultDto, HttpStatus.OK);
            }
        }
        return new ResponseEntity<CommonNoticeResultDto>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private int calcPageCnt(int total, int limit) {
        int pageCnt = 0;
        if (total % limit > 0) pageCnt = total / limit + 1;
        else pageCnt = total / limit;
        return pageCnt;
    }

    // 공지글 상세 조회
    @GetMapping("/notice/{noticeType}")
    public ResponseEntity<CommonNoticeResultDto> noticeDetail(@PathVariable(required = true) final int noticeType,
                                                              @RequestParam(required = true) int id) {
        CommonNoticeResultDto commonNoticeResultDto = new CommonNoticeResultDto();
        if (noticeType == 1) {
            NoticeResultDto notice = noticeService.noticeDetail(id);
            if (notice != null) {
                commonNoticeResultDto.setNotice(notice);
                return new ResponseEntity<CommonNoticeResultDto>(commonNoticeResultDto, HttpStatus.OK);
            }
        } else if (noticeType == 2) {
            ScheduleResultDto schedule = noticeService.scheduleDetail(id);
            if (schedule != null) {
                commonNoticeResultDto.setSchedule(schedule);
                return new ResponseEntity<CommonNoticeResultDto>(commonNoticeResultDto, HttpStatus.OK);
            }
        } else {
            MenuResultDto menu = noticeService.menuDetail(id);
            if (menu != null) {
                commonNoticeResultDto.setMenu(menu);
                return new ResponseEntity<CommonNoticeResultDto>(commonNoticeResultDto, HttpStatus.OK);
            }
        }
        return new ResponseEntity<CommonNoticeResultDto>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
