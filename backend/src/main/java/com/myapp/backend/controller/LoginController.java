package com.myapp.backend.controller;


import com.myapp.backend.domain.login.LoginDto;
import com.myapp.backend.domain.user.User;
import com.myapp.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


@RestController
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginDto loginDto, HttpServletResponse response) {
        User user = userRepository.findByUserId(loginDto.getUserId());
        if(user != null){
            int userType = user.getType();
            if(userType == 1){ // 학부모 

            }else if(userType == 2){ // 선생님

            }else if(userType == 9){ // 관리자

            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }
}
