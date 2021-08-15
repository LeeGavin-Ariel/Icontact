package com.myapp.backend.controller;

import com.myapp.backend.domain.dto.Result;
import com.myapp.backend.domain.dto.join.JoinDto;
import com.myapp.backend.domain.dto.mypage.ChangePasswordDto;
import com.myapp.backend.domain.dto.mypage.MyPageResultDto;
import com.myapp.backend.domain.entity.User;
import com.myapp.backend.service.JoinService;
import com.myapp.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/user")
public class UserController {

    private final int SUCCESS = 1;
    private final int FAIL = -1;

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    JoinService joinService;

    @PostMapping
    public ResponseEntity join(@RequestBody JoinDto joinDto) { return joinService.join(joinDto); }


    @PostMapping("/changepw")
    public ResponseEntity<List<String>> changePW(@RequestBody JoinDto joinDto) {
        return joinService.changePW(joinDto);
    }

    /**
     * mypage 정보 반화
     * 유저 조회(userId로 조회한다)
     */
    @GetMapping("/info/{userId}")
    public ResponseEntity<MyPageResultDto> retrieveUser(@PathVariable String userId) {
        MyPageResultDto findUser = userService.retrieveUser(userId);

        if (findUser != null) {
            return new ResponseEntity<MyPageResultDto>(findUser, HttpStatus.OK);
        }
        return new ResponseEntity<MyPageResultDto>(HttpStatus.NOT_FOUND);
    }


    /**
     * 모든 유저 조회
     */
    @GetMapping
    public ResponseEntity<List<User>> retrieveAllUser(@RequestParam(value = "code", required = true) String kinderCode) {
        List<User> userlist = userService.retrieveAllUser(kinderCode);

        if (userlist != null) {
            return new ResponseEntity<List<User>>(userlist, HttpStatus.OK);
        }
        return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);
    }

    /**
     * 우리반 유저 조회
     */
    @GetMapping("/class")
    public ResponseEntity<List<User>> retrieveAllClassUser(@RequestParam(value = "classCode", required = true) String classCode) {
        List<User> userlist = userService.retrieveAllClassUser(classCode);

        for (User u : userlist) {
            System.out.println(u.toString());

        }

        if (userlist != null) {
            return new ResponseEntity<List<User>>(userlist, HttpStatus.OK);
        }
        return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);
    }


    /**
     * 유저 계정 승인
     */
    @PutMapping("/approve/{userId}")
    public ResponseEntity<Result> approveUser(@PathVariable String userId) {
        boolean flag = userService.approveUser(userId);

        if (flag) {
            return new ResponseEntity<Result>(new Result(true, "가입 요청 승인 : " + userId), HttpStatus.OK);
        }
        return new ResponseEntity<Result>(new Result(false, "가입 승인에 실패하였습니다."), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 유저 계정 거절
     */
    @PutMapping("/reject/{userId}")
    public ResponseEntity<Result> rejectUser(@PathVariable String userId) {
        boolean flag = userService.rejectUser(userId);

        if (flag) {
            return new ResponseEntity<Result>(new Result(true, "가입 요청 거절 : " + userId), HttpStatus.OK);
        }
        return new ResponseEntity<Result>(new Result(false, "가입 거절에 실패하였습니다."), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 유저 계정 삭제
     */
    @PutMapping("/remove/{userId}")
    public ResponseEntity<Result> removeUser(@PathVariable String userId) {
        boolean flag = userService.removeUser(userId);

        if (flag) {
            return new ResponseEntity<Result>(new Result(true, "가입 요청 거절 : " + userId), HttpStatus.OK);
        }
        return new ResponseEntity<Result>(new Result(false, "가입 거절에 실패하였습니다."), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 유저 프로필사진 수정
     *
     * @param userId
     * @return
     */
    @PutMapping("/profileImg/{userId}")
    public ResponseEntity<Result> updateProfileImg(@RequestParam("profileImg") String photoKey,
                                                   @PathVariable String userId) throws IOException {

        int result = userService.updateProfileImg(photoKey, userId);

        if (result == SUCCESS) return new ResponseEntity(result, HttpStatus.OK);
        return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
    }

    /**
     * 유저 비밀번호 수정
     *
     * @param userId
     * @return
     */
    @PutMapping("/password/{userId}")
    public ResponseEntity<Result> updatePassword(
            @RequestBody ChangePasswordDto changePasswordDto,
            @PathVariable String userId) {
        boolean flag = userService.updatePassword(changePasswordDto);

        if (flag) {
            return new ResponseEntity<Result>(new Result(true, "정보 수정 완료, userid : " + userId), HttpStatus.OK);
        }
        return new ResponseEntity<Result>(new Result(false, "정보 수정에 실패하였습니다."), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 유저 상태코드 변경
     *
     * @param userId
     * @param stateCode , 변경할 코드로 들어온다
     * @return
     */
    @PutMapping("/stateCode/{userId}")
    public ResponseEntity<Result> changeStateCode(@PathVariable String userId, @RequestParam(value = "stateCode", required = true) int stateCode) {
        boolean flag = userService.changeStateCode(userId, stateCode);

        if (flag) {
            return new ResponseEntity<Result>(new Result(true, "상태코드 수정 완료, userid : " + userId), HttpStatus.OK);
        }
        return new ResponseEntity<Result>(new Result(false, "상태코드 수정에 실패하였습니다."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


