package com.myapp.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.backend.domain.dto.gps.GPSRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class GPSService {

    private final ObjectMapper objectMapper;
    private Map<String, GPSRoom> gpsRooms;

    @PostConstruct
    private void init(){
        gpsRooms = new LinkedHashMap<>();
    }

    public List<GPSRoom> findAllRoom(){
        return new ArrayList<>(gpsRooms.values());
    }

    public GPSRoom findRoomById(String roomId){
        return gpsRooms.get(roomId);
    }

    public GPSRoom createRoom(String code){
        if(gpsRooms.containsKey(code)){
            return gpsRooms.get(code);
        }
        GPSRoom gpsRoom = GPSRoom.builder()
                .code(code)
                .build();
        System.out.println(code);
        gpsRooms.put(code, gpsRoom);
        return gpsRoom;
    }

    public void deleteRoom(String code){
        if(gpsRooms.containsKey(code)){
            gpsRooms.remove(code);
        }
    }

    public <T> void sendMessage(WebSocketSession session, T message){
        try{
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
