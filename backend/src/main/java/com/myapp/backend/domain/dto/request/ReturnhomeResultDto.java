package com.myapp.backend.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnhomeResultDto {
    private String userId;
    private String kidName;
    private String profileImg;
    private int rhId;
    private String rhDate;
    private String rhTime;
    private String guardian;
    private String guardianTel;
    private String emergency;
    private String emergencyTel;
    private String createDate;
}
