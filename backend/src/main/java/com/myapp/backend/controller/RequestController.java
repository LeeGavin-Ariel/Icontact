package com.myapp.backend.controller;

import com.myapp.backend.domain.dto.request.DosageResultDto;
import com.myapp.backend.domain.dto.request.RequestDto;
import com.myapp.backend.domain.dto.request.RequestResultDto;
import com.myapp.backend.domain.dto.request.ReturnhomeResultDto;
import com.myapp.backend.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class RequestController {

    @Autowired
    RequestService requestService;

    // 요청사항 글 등록
    @PostMapping("/request")
    public ResponseEntity requestInsert(@RequestBody RequestDto requestDto) {
        int result = 0;

        if (requestDto.getRequestType() == 1) { // 투약
            result = requestService.dosageInsert(requestDto);
        } else { // 귀가
            result = requestService.returnhomeInsert(requestDto);
        }

        if (result == 1) { // 글 등록 성공
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 요청사항 글 수정
    @PutMapping("/request")
    public ResponseEntity requestUpdate(@RequestBody RequestDto requestDto) {
        int result = 0;
        if (requestDto.getRequestType() == 1) { // 투약
            result = requestService.dosageUpdate(requestDto);
        } else { // 귀가
            result = requestService.returnhomeUpdate(requestDto);
        }

        if (result == 1) { // 글 수정 성공
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // 요청사항 글 삭제
    @DeleteMapping("/request")
    public ResponseEntity requestDelete(@RequestParam(required = true) final int requestType,
                                        @RequestParam(required = true) final int id) {
        int result = 0;
        if (requestType == 1) { // 투약
            result = requestService.dosageDelete(id);
        } else { // 귀가
            result = requestService.returnhomeDelete(id);
        }

        if (result == 1) { // 글 삭제 성공
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 선생님 글 조회
    @GetMapping("/request/teacher")
    public ResponseEntity<RequestResultDto> teacherRequestList(@RequestParam(required = true) final int requestType,
                                                        @RequestParam(required = true) final String userId) {
        RequestResultDto requestResultDto = new RequestResultDto();

        if(requestType == 1){ // 투약
            List<DosageResultDto> dosageList = requestService.teacherDosageList(userId);
            if(dosageList != null){
                requestResultDto.setDosageList(dosageList);
                return new ResponseEntity<RequestResultDto>(requestResultDto, HttpStatus.OK);
            }
        } else { // 귀가
            List<ReturnhomeResultDto> returnhomeList = requestService.teacherReturnhomeList(userId);
            if(returnhomeList != null){
                requestResultDto.setReturnhomeList(returnhomeList);
                return new ResponseEntity<RequestResultDto>(requestResultDto, HttpStatus.OK);
            }
        }

        return new ResponseEntity<RequestResultDto>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 학부모 글 조회
    @GetMapping("/request/parents")
    public ResponseEntity<RequestResultDto> parentRequestList(@RequestParam(required = true) final int requestType,
                                                               @RequestParam(required = true) final String userId) {
        RequestResultDto requestResultDto = new RequestResultDto();

        if(requestType == 1){ // 투약
            List<DosageResultDto> dosageList = requestService.parentDosageList(userId);
            if(dosageList != null){
                requestResultDto.setDosageList(dosageList);
                return new ResponseEntity<RequestResultDto>(requestResultDto, HttpStatus.OK);
            }
        } else { // 귀가
            List<ReturnhomeResultDto> returnhomeList = requestService.parentReturnhomeList(userId);
            if(returnhomeList != null){
                requestResultDto.setReturnhomeList(returnhomeList);
                return new ResponseEntity<RequestResultDto>(requestResultDto, HttpStatus.OK);
            }
        }

        return new ResponseEntity<RequestResultDto>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 글 상세 조회
    @GetMapping("/request")
    public ResponseEntity<RequestResultDto> requestDetail(@RequestParam(required = true) final int requestType,
                                                          @RequestParam(required = true) final int id){
        RequestResultDto requestResultDto = new RequestResultDto();

        if(requestType == 1){ // 투약
            DosageResultDto dosageResultDto = requestService.dosageDetail(id);
            if(dosageResultDto != null){
                requestResultDto.setDosage(dosageResultDto);
                return new ResponseEntity<RequestResultDto>(requestResultDto, HttpStatus.OK);
            }
        } else { // 귀가
            ReturnhomeResultDto returnhomeResultDto = requestService.returnhomeDetail(id);
            requestResultDto.setReturnhome(returnhomeResultDto);
            return new ResponseEntity<RequestResultDto>(requestResultDto, HttpStatus.OK);
        }

        return new ResponseEntity<RequestResultDto>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
