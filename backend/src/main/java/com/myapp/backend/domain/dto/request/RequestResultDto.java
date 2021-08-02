package com.myapp.backend.domain.dto.request;

import com.myapp.backend.domain.entity.Dosage;
import com.myapp.backend.domain.entity.Returnhome;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestResultDto {
    // list 반환시 총 페이지 수 함께 반환
    int pageCnt;

    DosageResultDto dosage;
    List<DosageResultDto> dosageList;

    ReturnhomeResultDto returnhome;
    List<ReturnhomeResultDto> returnhomeList;

}
