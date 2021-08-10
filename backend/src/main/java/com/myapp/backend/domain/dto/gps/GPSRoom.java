package com.myapp.backend.domain.dto.gps;

import com.myapp.backend.service.GPSService;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Data
public class GPSRoom {

    private String roomId;
    private String name;//== 유치원 code
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public GPSRoom(String roomId, String name){
        this.roomId=roomId;
        this.name=name;
    }

    public void handleActions(WebSocketSession session, GPSMessage gpsMessage, GPSService gpsService){

        System.out.println("OK!");
        if(gpsMessage.getType().equals("Update")){
            sessions.add(session);
            System.out.println("OK!");
        }
        sendMessage(gpsMessage, gpsService);
    }

    public <T> void sendMessage(T message, GPSService gpsService){
        sessions.parallelStream().forEach(session -> gpsService.sendMessage(session, message));
    }


}
