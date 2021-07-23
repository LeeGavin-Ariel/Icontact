package com.myapp.backend.service;

import com.myapp.backend.domain.login.LoginDto;
import com.myapp.backend.domain.login.TokenUpdateDto;
import com.myapp.backend.domain.user.User;
import com.myapp.backend.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class LoginService {

    @Autowired
    LoginRepository loginRepository;

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


}
