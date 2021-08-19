package com.myapp.backend.controller;

import com.myapp.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/chat")
public class ChatController {

    private final int SUCCESS = 1;
    private final int FAIL = -1;

    private UserService userService;

    @Autowired
    public ChatController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<String> retrieveUserProfileImg(@PathVariable String userId) {
        String findUser = userService.retrieveUser(userId).getProfileImg();

        if (findUser != null) {
            return new ResponseEntity<String>(findUser, HttpStatus.OK);
        }
        return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/stateCode/{userId}")
    public ResponseEntity<Integer> retrieveUserStateCode(@PathVariable String userId) {
        int stateCode = userService.retrieveUser(userId).getStateCode();
        return new ResponseEntity<Integer>(stateCode, HttpStatus.OK);
    }
}


