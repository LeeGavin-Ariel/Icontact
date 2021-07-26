package com.myapp.backend.controller;

import com.myapp.backend.domain.dto.login.LoginDto;
import com.myapp.backend.domain.dto.login.LoginResultDto;
import com.myapp.backend.domain.dto.request.RequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class RequestController {

    @PostMapping("/request")
    public ResponseEntity<LoginResultDto> login(@RequestBody RequestDto RequestDto) {
        return null;
    }
}
