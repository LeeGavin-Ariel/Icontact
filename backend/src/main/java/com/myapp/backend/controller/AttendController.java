package com.myapp.backend.controller;

import com.myapp.backend.domain.dto.attend.AttendDto;
import com.myapp.backend.domain.dto.notebook.NoteBookCreateDto;
import com.myapp.backend.domain.entity.Attend;
import com.myapp.backend.service.AttendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/attendance")
public class AttendController {

    @Autowired
    AttendService attendService;

    @PostMapping
    public ResponseEntity<Integer> writeAttend(@RequestBody AttendDto attendDto) throws Exception{

        int result = attendService.writeAttend(attendDto.getUserId(), attendDto.getDate() );

        if(result!=-1){
            return new ResponseEntity(result, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

    }


    @GetMapping("/parents")
    public ResponseEntity<List<AttendDto>> attendParents(@RequestParam String userId, @RequestParam String date){
        try {
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = transFormat.parse(date);

            System.out.println(parsedDate);

            List<AttendDto> result = attendService.attendParents(userId, parsedDate);
            return new ResponseEntity(result, HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/teacher")
    public ResponseEntity<List<AttendDto>> attendTeacher(@RequestParam String userId, @RequestParam String date){
        try {
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = transFormat.parse(date);

            List<AttendDto> result = attendService.attendTeacher(userId, parsedDate);
            return new ResponseEntity(result, HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
