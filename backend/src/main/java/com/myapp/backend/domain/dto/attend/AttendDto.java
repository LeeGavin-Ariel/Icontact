package com.myapp.backend.domain.dto.attend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendDto {

    String userId;
    String kidName;
    String profileImg;
    Date date;
    int attend;
}
