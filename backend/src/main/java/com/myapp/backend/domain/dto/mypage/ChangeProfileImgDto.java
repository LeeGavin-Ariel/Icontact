package com.myapp.backend.domain.dto.mypage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeProfileImgDto {
    private String userId;
    private String Password;
    private String profileImg;
}
