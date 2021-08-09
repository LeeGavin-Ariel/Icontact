package com.myapp.backend.controller;


import com.myapp.backend.domain.dto.join.AuthDto;
import com.myapp.backend.service.AuthService;
import com.myapp.backend.service.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/auth")
public class AuthController {
    //핸드폰 인증 관련 컨트롤러

    @Autowired
    AuthService authService;

    @Autowired
    JoinService joinService;

    @PostMapping("/sms")
    public ResponseEntity sendSms(@RequestBody AuthDto autoDto) {
        if(authService.sendSms(autoDto)){
            return new ResponseEntity(HttpStatus.OK);
        }
        else{
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/kinder/class")
    public ResponseEntity<List<String[]>> classList(@RequestParam String kinderCode){
        return joinService.classList(kinderCode);
    }

    @GetMapping("/sms")
    public ResponseEntity authSms(@RequestParam String phoneNum, @RequestParam String code) {
        if(authService.authSms(phoneNum, code)){
            return new ResponseEntity(HttpStatus.OK);
        }
        else{
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

    }



}
