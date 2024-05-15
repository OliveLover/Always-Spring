package com.example.alwaysSpring.domain.tokens;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tokenValue;

    @Column(nullable = false)
    private String username;

    public RefreshToken updateToken(String tokenValue) {
        this.tokenValue = tokenValue;
        return this;
    }

    @Builder
    public RefreshToken(String tokenValue, String username) {
        this.tokenValue = tokenValue;
        this.username = username;
    }
}
