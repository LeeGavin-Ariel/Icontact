package com.myapp.backend.domain.dto.mypage;

import com.myapp.backend.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//비밀 번호 변경 요청용
public class ChangeProfileImgDto {
    private String userId;
    private String Password;
    private String profileImg;
}
