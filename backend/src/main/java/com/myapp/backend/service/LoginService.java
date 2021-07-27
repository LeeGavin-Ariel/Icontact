package com.myapp.backend.service;

import com.myapp.backend.domain.entity.KinderClass;
import com.myapp.backend.domain.dto.login.LoginDto;
import com.myapp.backend.domain.dto.login.LoginResultDto;
import com.myapp.backend.domain.dto.login.TokenUpdateDto;
import com.myapp.backend.domain.entity.User;
import com.myapp.backend.repository.KinderRepository;
import com.myapp.backend.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.myapp.backend.domain.user.Kid;
import com.myapp.backend.domain.user.Teacher;
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
    public LoginResultDto setLoginResult(User user) {
        LoginResultDto loginResultDto = new LoginResultDto();

        loginResultDto.setUserId(user.getUserId());
        loginResultDto.setUserName(user.getUserName());
        loginResultDto.setType(user.getType());
        loginResultDto.setAccept(user.getAccept());
        loginResultDto.setStateCode(user.getStateCode());
        loginResultDto.setProfileImg(user.getProfileImg());
        loginResultDto.setKidName(user.getKidName());

        KinderClass kinderclass = kinderRepository.findByClassCode(user.getClassCode());
        loginResultDto.setKinderCode(kinderclass.getKinder().getKinderCode());
        loginResultDto.setKinderName(kinderclass.getKinder().getKinderName());
        loginResultDto.setClassCode(kinderclass.getClassCode());
        loginResultDto.setClassName(kinderclass.getClassName());

        return loginResultDto;
    }
}
