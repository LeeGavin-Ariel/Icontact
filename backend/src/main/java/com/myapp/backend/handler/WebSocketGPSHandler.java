package com.myapp.backend.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.backend.domain.dto.gps.GPSMessage;
import com.myapp.backend.domain.dto.gps.GPSRoom;
import com.myapp.backend.service.GPSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketGPSHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;

    private final GPSService gpsService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage msg) throws Exception{

        String payload = msg.getPayload();
        System.out.println(payload);
        log.info("payload {}", payload);
//        TextMessage text = new TextMessage("Welcome!");
//        session.sendMessage(text);
        GPSMessage gpsMessage = objectMapper.readValue(payload, GPSMessage.class);

        GPSRoom room = gpsService.findRoomById(gpsMessage.getCode());
        System.out.println(room);
        room.handleActions(session, gpsMessage, gpsService);
    }

}
