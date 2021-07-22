package com.myapp.backend.service;

import com.myapp.backend.domain.dto.AuthDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

public interface AuthService {

    public boolean sendSms(AuthDto autoDto);

    public boolean authSms(String phoneNum, String code);
}
