package com.myapp.backend.controller;

import com.myapp.backend.domain.dto.gps.GPSDto;
import com.myapp.backend.domain.dto.gps.GPSRoom;
import com.myapp.backend.service.GPSService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/gps")
public class GPSController {

    private final GPSService gpsService;

    @PostMapping
    public GPSRoom createRoom(@RequestBody GPSDto gpsDto){
        System.out.println(gpsDto.getName());
        return gpsService.createRoom(gpsDto.getName());

    }



}
