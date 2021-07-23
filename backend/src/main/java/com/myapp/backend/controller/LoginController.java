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
            int userType = user.getType();

            LoginResultDto loginResultDto = loginService.setLoginResult(userType, user);

            if (loginResultDto.getAccept() == 2){
                TokenDto tokenDto = jwtService.create("userid", loginResultDto.getUserId(), "user");

                response.setHeader("jwt-access-token", tokenDto.getAccessToken());
                response.setHeader("jwt-refresh-token", tokenDto.getRefreshToken());

                int result = loginService.setRefreshToken(
                        new TokenUpdateDto(tokenDto.getRefreshToken(), loginResultDto.getUserId()));

                if (result != 1) {
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
            return new ResponseEntity<>(loginResultDto, HttpStatus.OK);

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
