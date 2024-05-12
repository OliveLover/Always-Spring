package com.example.alwaysSpring.domain.users;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLL_USER", "일반 사용자"),
    ADMIN("ROLL_ADMIN", "관리자");

    private final String key;
    private final String tittle;
}
