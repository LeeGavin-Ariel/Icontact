package com.myapp.backend.domain.dto.gps;

import com.myapp.backend.service.GPSService;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Data
public class GPSRoom {

    private String code;//== 유치원 code
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public GPSRoom(String code){
        this.code=code;
    }

    public void handleActions(WebSocketSession session, GPSMessage gpsMessage, GPSService gpsService){
        //선생님 : ON으로 토글될 때
        //학부모 : 페이지에 들어갈 때(created) Enter한다
        if(gpsMessage.getType().equals("Enter") && !sessions.contains(session)){
            sessions.add(session);
        }
        //선생님 : 1초마다
        if(gpsMessage.getType().equals("Update")){
            sendMessage(gpsMessage, gpsService);
        }
        if(gpsMessage.getType().equals("Delete")){
            sendMessage(gpsMessage, gpsService);
        }

    }

    public <T> void sendMessage(T message, GPSService gpsService){
        sessions.parallelStream().forEach(session -> gpsService.sendMessage(session, message));
    }


}
