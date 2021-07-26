package com.myapp.backend.controller;


import com.myapp.backend.domain.dto.login.LoginDto;
import com.myapp.backend.domain.dto.login.LoginResultDto;
import com.myapp.backend.domain.dto.login.TokenDto;
import com.myapp.backend.domain.dto.login.TokenUpdateDto;
import com.myapp.backend.domain.entity.User;
import com.myapp.backend.repository.KinderRepository;
import com.myapp.backend.repository.LoginRepository;
import com.myapp.backend.service.JwtService;
import com.myapp.backend.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


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
            int userType = user.getType(); // 회원 유형
            int userAccept = user.getAccept(); // 승인상태

            LoginResultDto loginResultDto = null;

            if(userAccept == 2){ // 승인완료
                loginResultDto = loginService.setLoginResult(user);
                TokenDto tokenDto = jwtService.create("userid", loginResultDto.getUserId(), "user");

                response.setHeader("jwt-access-token", tokenDto.getAccessToken());
                response.setHeader("jwt-refresh-token", tokenDto.getRefreshToken());

                int result = loginService.setRefreshToken(
                        new TokenUpdateDto(tokenDto.getRefreshToken(), loginResultDto.getUserId()));

                if (result != 1) {
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                } else {
                    return new ResponseEntity<>(loginResultDto, HttpStatus.OK);
                }
            } else { // 미승인, 승인거절
                loginResultDto.setAccept(userAccept);
                return new ResponseEntity<>(loginResultDto, HttpStatus.OK);
            }


        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    @GetMapping("/logout")
    public Object logout(@RequestParam(required = true) final String userId){
        User user = loginRepository.findByUserId(userId);
        if(user != null){
            user.setStateCode(3);
            loginRepository.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}