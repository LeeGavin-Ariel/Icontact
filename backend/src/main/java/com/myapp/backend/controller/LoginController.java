package com.myapp.backend.controller;


import com.myapp.backend.domain.kinder.KinderClass;
import com.myapp.backend.domain.login.LoginDto;
import com.myapp.backend.domain.login.LoginResultDto;
import com.myapp.backend.domain.login.TokenDto;
import com.myapp.backend.domain.login.TokenUpdateDto;
import com.myapp.backend.domain.user.Kid;
import com.myapp.backend.domain.user.Teacher;
import com.myapp.backend.domain.user.User;
import com.myapp.backend.repository.KinderRepository;
import com.myapp.backend.repository.LoginRepository;
import com.myapp.backend.repository.UserRepository;
import com.myapp.backend.service.JwtService;
import com.myapp.backend.service.LoginService;
import jdk.internal.dynalink.support.CallSiteDescriptorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
public class LoginController {

    @Autowired
    LoginRepository loginRepository;

    @Autowired
    KinderRepository kinderRepository;

    @Autowired
    JwtService jwtService;

    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginResultDto> login(@RequestBody LoginDto loginDto, HttpServletResponse response) {
        User user = loginRepository.findByUserIdAndPassword(loginDto.getUserId(), loginDto.getPassword());
        if(user != null){
            LoginResultDto loginResultDto = new LoginResultDto();
            int userType = user.getType();
            if(userType == 1){ // 학부모

                Kid kid = user.getKids().get(0);

                if(kid.getAccept() == 2){

                    loginResultDto.setUserId(user.getUserId());
                    loginResultDto.setUserName(user.getUserName());
                    loginResultDto.setType(user.getType());
                    loginResultDto.setKidId(kid.getKidId());
                    loginResultDto.setKidName(kid.getKidName());
                    loginResultDto.setAccept(kid.getAccept());
                    loginResultDto.setProfileImg(kid.getProfileImg());

                    KinderClass kinderclass = kinderRepository.findByClassCode(kid.getClassCode());
                    loginResultDto.setKinderCode(kinderclass.getKinder().getKinderCode());
                    loginResultDto.setKinderName(kinderclass.getKinder().getKinderName());
                    loginResultDto.setClassCode(kinderclass.getClassCode());
                    loginResultDto.setClassName(kinderclass.getClassName());

                } else {
                    loginResultDto.setAccept(kid.getAccept());
                    return new ResponseEntity<>(loginResultDto, HttpStatus.OK);
                }

            }else if(userType == 2){ // 선생님

                Teacher teacher = user.getTeacher();
                if(teacher.getAccept() == 2) {
                    loginResultDto.setUserId(user.getUserId());
                    loginResultDto.setUserName(user.getUserName());
                    loginResultDto.setType(user.getType());
                    loginResultDto.setAccept(teacher.getAccept());
                    loginResultDto.setStateCode(teacher.getStateCode());
                    loginResultDto.setProfileImg(teacher.getProfileImg());

                    KinderClass kinderclass = kinderRepository.findByClassCode(teacher.getClassCode());
                    loginResultDto.setKinderCode(kinderclass.getKinder().getKinderCode());
                    loginResultDto.setKinderName(kinderclass.getKinder().getKinderName());
                    loginResultDto.setClassCode(kinderclass.getClassCode());
                    loginResultDto.setClassName(kinderclass.getClassName());
                } else {
                    loginResultDto.setAccept(teacher.getAccept());
                    return new ResponseEntity<>(loginResultDto, HttpStatus.OK);
                }

            }else if(userType == 9){ // 관리자
                Teacher teacher = user.getTeacher();
                KinderClass kinderclass = kinderRepository.findByClassCode(teacher.getClassCode());
                loginResultDto.setKinderCode(kinderclass.getKinder().getKinderCode());
                loginResultDto.setKinderName(kinderclass.getKinder().getKinderName());
            }

            TokenDto tokenDto = jwtService.create("userid", loginResultDto.getUserId(), "user");

            response.setHeader("jwt-access-token", tokenDto.getAccessToken());
            response.setHeader("jwt-refresh-token", tokenDto.getRefreshToken());

            int result = loginService.setRefreshToken(
                    new TokenUpdateDto(tokenDto.getRefreshToken(), loginResultDto.getUserId()));

            if (result == 1) {
                return new ResponseEntity<>(loginResultDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    @GetMapping("/logout")
    public Object logout(@RequestParam(required = true) final String userId){
        User user = loginRepository.findByUserId(userId);
        if(user != null){
            user.getTeacher().setStateCode(3);
            loginRepository.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
