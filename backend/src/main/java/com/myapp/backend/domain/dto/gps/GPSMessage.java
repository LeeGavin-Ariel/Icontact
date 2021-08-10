package com.myapp.backend.domain.dto.gps;

import lombok.Data;

@Data
public class GPSMessage {
    private String type; // update, enter
    private String code;//유치원/반 코드
    private String lat;//위도
    private String lon;//경도
}
