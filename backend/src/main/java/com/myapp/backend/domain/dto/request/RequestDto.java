package com.myapp.backend.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {
    // 어느 요청인지를 입력받아야할듯
    private int requestType;
    private String userId;

    // dosage(투약)
    private String symptom;
    private String medicineType;
    private int dosageVol;
    private int dosageCnt;
    private String dosageTime;
    private String storage;
    private String specialNote;

    // returnhome(귀가)
    private String rhDate;
    private String rhTime;
    private String guardian;
    private String guardianTel;
    private String emergency;
    private String emergencyTel;

}
