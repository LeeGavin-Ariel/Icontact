package com.myapp.backend.domain.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtPayloadDto {

    private String sub;
    private int exp;
    private int iat;
    private String userId;
}
