package com.myapp.backend.service;

import com.myapp.backend.domain.kinder.KinderClass;
import com.myapp.backend.domain.login.LoginDto;
import com.myapp.backend.domain.login.LoginResultDto;
import com.myapp.backend.domain.login.TokenUpdateDto;
import com.myapp.backend.domain.user.Kid;
import com.myapp.backend.domain.user.Teacher;
import com.myapp.backend.domain.user.User;
import com.myapp.backend.repository.KinderRepository;
import com.myapp.backend.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class LoginService {

    @Autowired
    LoginRepository loginRepository;

    @Autowired
    KinderRepository kinderRepository;

    private static final int SUCCESS = 1;
    private static final int FAIL = -1;

    @Transactional
    public User login(LoginDto loginDto){
        User user = loginRepository.findByUserId(loginDto.getUserId());

        if(user != null && user.getPassword().equals(loginDto.getPassword())) {
            return user;
        } else {
            return null;
        }
    }

    @Transactional
    public int setRefreshToken(TokenUpdateDto tokenUpdateDto){
        try{
            User user = loginRepository.findByUserId(tokenUpdateDto.getUserId());

            if(user != null){
                user.setRefreshToken(tokenUpdateDto.getRefreshToken());
                loginRepository.save(user);
            } else return FAIL;
            return SUCCESS;
        } catch (Exception e){
            e.printStackTrace();
            return FAIL;
        }
    }

    @Transactional
    public String getRefreshToken(String userId){
        User user = loginRepository.findByUserId(userId);
        if(user != null){
            return user.getRefreshToken();
        }
        return null;
    }

    @Transactional
    public LoginResultDto setLoginResult(int type, User user) {
        LoginResultDto loginResultDto = new LoginResultDto();
        if(type == 1){ // 학부모
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
            }

        }else if(type == 2){ // 선생님

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
            }

        }else { // 관리자
            Teacher teacher = user.getTeacher();
            KinderClass kinderclass = kinderRepository.findByClassCode(teacher.getClassCode());
            loginResultDto.setKinderCode(kinderclass.getKinder().getKinderCode());
            loginResultDto.setKinderName(kinderclass.getKinder().getKinderName());
        }

        return loginResultDto;
    }



}
