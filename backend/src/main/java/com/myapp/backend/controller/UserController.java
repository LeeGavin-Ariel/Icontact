package com.myapp.backend.controller;

import com.myapp.backend.domain.dto.Result;
import com.myapp.backend.domain.dto.admin.AdminUserDto;
import com.myapp.backend.domain.entity.Kid;
import com.myapp.backend.domain.entity.Teacher;
import com.myapp.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 학부모 계정 조회(kidId로 조회한다)
     * profileId => kidId
     */
    @GetMapping("/parents/{profileId}")
    public ResponseEntity<Kid> retrieveKid(@PathVariable int profileId) {
        Kid kid = userService.retrieveKid(profileId);

        if (kid !=null) {
            return new ResponseEntity<Kid>(kid, HttpStatus.OK);
        }
        return new ResponseEntity<Kid>(HttpStatus.NOT_FOUND);
    }

    /**
     * 학부모 계정 승인(kidId로 승인한다)
     * profileId => kidId
     */
    @PutMapping("/parents/{profileId}")
    public ResponseEntity<Result> approveKid(@PathVariable int profileId) {
        boolean flag = userService.approveKid(profileId);

        if (flag) {
            return new ResponseEntity<Result>(new Result(true, "가입 요청 승인 : " + profileId), HttpStatus.OK);
        }
        return new ResponseEntity<Result>(new Result(false, "가입 승인에 실패하였습니다."), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 학부모 계정 거절(kidId로 거절한다)
     * profileId => kidId
     */
    @PutMapping("/parents/reject/{profileId}")
    public ResponseEntity<Result> rejectKid(@PathVariable int profileId) {
        boolean flag = userService.rejectKid(profileId);

        if (flag) {
            return new ResponseEntity<Result>(new Result(true, "가입 요청 거절, kidid : " + profileId), HttpStatus.OK);
        }
        return new ResponseEntity<Result>(new Result(false, "가입 거절에 실패하였습니다."), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 선생님 계정 조회(userId 조회한다)
     * profileId => kidId
     */
    @GetMapping("/teacher/{userId}")
    public ResponseEntity<Teacher> retrieveTeacher(@PathVariable String userId) {
        Teacher teacher = userService.retrieveTeacher(userId);

        if (teacher !=null) {
            System.out.println(teacher.toString());
            return new ResponseEntity<Teacher>(teacher, HttpStatus.OK);
        }
        return new ResponseEntity<Teacher>(HttpStatus.NOT_FOUND);
    }

    /**
     * 선생님 계정 승인
     * 기능은 완료, 코드 다듬기
     */
    @PutMapping("/teacher/{userId}")
    public ResponseEntity<Result> approveTeacher(@PathVariable String userId) {
        boolean flag = userService.approveTeacher(userId);

        if (flag) {
            return new ResponseEntity<Result>(new Result(true, "가입 요청 승인, userid : " + userId), HttpStatus.OK);
        }
        return new ResponseEntity<Result>(new Result(false, "가입 승인에 실패하였습니다."), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 선생님 계정 거절
     * 기능은 완료, 코드 다듬기
     */
    @PutMapping("/teacher/reject/{userId}")
    public ResponseEntity<Result> rejectTeacher(@PathVariable String userId) {
        boolean flag = userService.rejectTeacher(userId);

        if (flag) {
            return new ResponseEntity<Result>(new Result(true, "가입 요청 거절, userid : " + userId), HttpStatus.OK);
        }
        return new ResponseEntity<Result>(new Result(false, "가입 거절에 실패하였습니다."), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /**
     * 관리자 계정 소속 유치원의 모든 유저(학부모, 선생님) 조회
     */
    @GetMapping("/admin")
    public List<AdminUserDto> retrieveUserInKindergarden(@RequestParam(value = "code", required = true) String kinderCode) {
        List<AdminUserDto> adminUserList = userService.retrieveAllUser(kinderCode);
        return adminUserList;
    }


}


