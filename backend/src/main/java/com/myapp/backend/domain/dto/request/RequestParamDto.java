package com.myapp.backend.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestParamDto {
    private int requestType;
    private String userId;
    private int pageNum;

    private int limit;
    private int offset;


}
