package com.myapp.backend.controller;

import com.myapp.backend.domain.dto.JoinDto;
import com.myapp.backend.service.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    JoinService joinService;

    @PostMapping
    public ResponseEntity join(@RequestBody JoinDto joinDto){
        return joinService.join(joinDto);
    }

    @GetMapping("/kinder/class")
    public ResponseEntity<List<String>> classList(@RequestParam String kinderCode){
        return joinService.classList(kinderCode);
    }
}
