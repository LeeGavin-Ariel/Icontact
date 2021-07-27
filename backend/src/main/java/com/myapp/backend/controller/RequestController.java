package com.myapp.backend.controller;

import com.myapp.backend.domain.dto.request.RequestDto;
import com.myapp.backend.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RequestController {

    @Autowired
    RequestService requestService;

    // 요청사항 글 등록
    @PostMapping("/request")
    public ResponseEntity insertRequest(@RequestBody RequestDto requestDto) {
        int result = 0;

        if(requestDto.getRequestType() == 1){ // 투약
            result = requestService.insertDosage(requestDto);
        } else { // 귀가
            result = requestService.insertReturnhome(requestDto);
        }

        if(result == 1){ // 글 등록 성공
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
