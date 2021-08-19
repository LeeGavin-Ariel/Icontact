package com.myapp.backend.domain.dto.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenUpdateDto {

    private String refreshToken;
    private String userId;
}
