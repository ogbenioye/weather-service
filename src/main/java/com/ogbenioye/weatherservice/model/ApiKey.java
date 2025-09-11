package com.ogbenioye.weatherservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity @Table(name = "apiKeys")
@NoArgsConstructor
@Getter @Setter
public class ApiKey {
    @Id @UuidGenerator
    private String id;
    private String apiKey;
    @ManyToOne(fetch = FetchType.LAZY)
    private ApplicationUser owner;
    private boolean isActive = true;
    private LocalDateTime createdAt =  LocalDateTime.now();
    private LocalDateTime updatedAt =  LocalDateTime.now();
    private LocalDateTime expiresAt;

    public ApiKey(String apiKey,  ApplicationUser owner) {
        this.apiKey = apiKey;
        this.owner = owner;
    }
}
