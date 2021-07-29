package com.myapp.backend.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DosageResultDto {
    private String userId;
    private String kidName;
    private int dosageId;
    private String symptom;
    private String medicineType;
    private int dosageVol;
    private int dosageCnt;
    private String dosageTime;
    private String storage;
    private String specialNote;
    private Date createDate;
}
