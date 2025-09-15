package com.ogbenioye.weatherservice.service;

import com.ogbenioye.weatherservice.dto.ApiResponse;
import com.ogbenioye.weatherservice.model.ApiKey;
import com.ogbenioye.weatherservice.repository.ApiKeyRepository;
import com.ogbenioye.weatherservice.repository.ApplicationUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class ApiKeyService {

    private final ApiKeyRepository apikeyRepo;
    private final ApplicationUserRepository userRepository;

    public ApiKeyService(ApiKeyRepository apiKeyRepository, ApplicationUserRepository userRepository) {
        this.apikeyRepo = apiKeyRepository;
        this.userRepository = userRepository;
    }

    public ApiResponse<ApiKey> generateNewKey(String ownerId) {
        try {
            var user = userRepository.findById(ownerId);
            if (user.isEmpty()) {
                return new ApiResponse<>(
                        false,
                        "An error occurred",
                        HttpStatus.CONFLICT,
                        null
                );
            }

            var apiKey = new ApiKey(
                    generateKey(),
                    user.get()
            );

            apikeyRepo.save(apiKey);
            return new ApiResponse<>(
                    true,
                    "Api key generated successfully",
                    HttpStatus.CREATED,
                    apiKey
            );
        }
        catch (Exception ex) {
            return new ApiResponse<>(
                    false,
                    "An error occurred",
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null
            );
        }
    }

    private String generateKey() {
        var random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    public ApiResponse<Boolean> invalidateApikey(String apiKey) {
        try {
            var response = apikeyRepo.findByApiKey(apiKey);
            if (response.isEmpty()) {
                return new ApiResponse<Boolean>(
                        false,
                        "Api key no longer exists",
                        HttpStatus.NOT_FOUND,
                        false
                );
            }

            var key = response.get();
            key.setActive(false);

            apikeyRepo.save(key);
            return new ApiResponse<Boolean>(
                    true,
                    "Invalidation Successful!",
                    HttpStatus.OK,
                    true
            );
        }
        catch (Exception ex) {
            return new ApiResponse<Boolean>(
                    false,
                    "An error occurred",
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    false
            );
        }
    };

    public ApiResponse<Boolean> deleteKey(String apiKey) {
        try {
            var response = apikeyRepo.findByApiKey(apiKey);
            if (response.isEmpty()) {
                return new ApiResponse<Boolean>(
                        false,
                        "Api key no longer exists",
                        HttpStatus.NOT_FOUND,
                        false
                );
            }

            var key = response.get();
            apikeyRepo.delete(key);

            return new ApiResponse<Boolean>(
                    true,
                    "Delete Successful!",
                    HttpStatus.OK,
                    true
            );
        }
        catch (Exception ex) {
            return new ApiResponse<Boolean>(
                    false,
                    "An error occurred",
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    false
            );
        }
    }

    public ApiResponse<ApiKey> getApiKey(String apiKey) {
        var response = apikeyRepo.findByApiKey(apiKey);
        if (response.isEmpty()) {
            return new ApiResponse<ApiKey>(
                    false,
                    "Api key no longer exists",
                    HttpStatus.NOT_FOUND,
                    null
            );
        }

        return new ApiResponse<ApiKey>(
                true,
                "Retrieved Successfully!",
                HttpStatus.OK,
                response.get()
        );
    }

    public ApiResponse<List<ApiKey>> getUserApikeys(String ownerId) {
        try {
            var owner  = userRepository.findById(ownerId);
            if (owner.isEmpty()) {
                return new ApiResponse<List<ApiKey>>(
                        false,
                        "User not found",
                        HttpStatus.NOT_FOUND,
                        null
                );
            }

            var keys = apikeyRepo.findAllByOwner(owner.get());
            return new ApiResponse<List<ApiKey>>(
                    true,
                    String.format("Successfully retrieved %d keys", keys.size()),
                    HttpStatus.OK,
                    keys
            );
        }
        catch (Exception ex) {
            return new ApiResponse<List<ApiKey>>(
                    false,
                    "An error occurred",
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null
            );
        }
    }

    public Boolean isValid(String apiKey) {
        return apikeyRepo.findByApiKey(apiKey).stream().anyMatch(ApiKey::isActive);
    }
}
