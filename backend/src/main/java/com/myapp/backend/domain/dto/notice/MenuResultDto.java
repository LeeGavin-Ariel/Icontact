package com.myapp.backend.domain.dto.notice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuResultDto {
    private String userId;
    private String userName;
    private int menuId;
    private String amSnack;
    private String amSnackImgUrl;
    private String lunch;
    private String lunchImgUrl;
    private String pmSnack;
    private String pmSnackImgUrl;
    private String createDate;
}

