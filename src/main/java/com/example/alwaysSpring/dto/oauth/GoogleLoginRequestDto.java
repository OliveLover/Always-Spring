package com.example.alwaysSpring.dto.oauth;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoogleLoginRequestDto {
    private String credential;
    private String clientId;

    public GoogleLoginRequestDto (String credential, String clientId) {
        this.credential = credential;
        this.clientId = clientId;
    }
}
