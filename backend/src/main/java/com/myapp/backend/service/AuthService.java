package com.myapp.backend.service;

import com.myapp.backend.domain.dto.join.AuthDto;

public interface AuthService {

    public boolean sendSms(AuthDto autoDto);

    public boolean authSms(String phoneNum, String code);
}
