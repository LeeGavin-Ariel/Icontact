package com.myapp.backend.controller;

import com.myapp.backend.domain.dto.Result;
import com.myapp.backend.domain.entity.Post;
import com.myapp.backend.service.SNSService;
import com.myapp.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class SNSController {

    private SNSService snsService;

    @Autowired
    public SNSController(SNSService snsService) {
        this.snsService = snsService;
    }

    /**
     * 나의 게시글 조회
     */
    @GetMapping("/{profileId}")
    public ResponseEntity<Result> retrievePost(@PathVariable int profileId) {
//        List<Post> flag = snsService.retrievePost(profileId);
//
//        if (flag) {
//            return new ResponseEntity<Result>(new Result(true, "가입 요청 승인 : " + profileId), HttpStatus.OK);
//        }
        return new ResponseEntity<Result>(new Result(false, "가입 승인에 실패하였습니다."), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}


