package com.myapp.backend.service;

import com.myapp.backend.domain.dto.JoinDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface JoinService {

    public ResponseEntity join(@RequestBody JoinDto joinDto);
    public ResponseEntity<List<String>> classList(@RequestParam String kinderCode);

    public ResponseEntity changePW(@RequestBody JoinDto joinDto);
}
