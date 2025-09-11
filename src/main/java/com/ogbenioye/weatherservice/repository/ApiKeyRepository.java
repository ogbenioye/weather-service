package com.ogbenioye.weatherservice.repository;

import com.ogbenioye.weatherservice.model.ApiKey;
import com.ogbenioye.weatherservice.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, String> {
    Optional<ApiKey> findByApiKey(String apiKey);
    Optional<List<ApiKey>> findAllByOwner(ApplicationUser owner);
    Optional<List<ApiKey>> findAllByOwnerId(String apiKey, String owner);
}
