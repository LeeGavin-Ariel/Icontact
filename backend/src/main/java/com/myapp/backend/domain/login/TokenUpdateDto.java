package com.myapp.backend.domain.login;

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
