package com.ogbenioye.weatherservice.controller;

import com.ogbenioye.weatherservice.dto.ApiResponse;
import com.ogbenioye.weatherservice.model.ApiKey;
import com.ogbenioye.weatherservice.service.ApiKeyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/keys")
public class ApiKeyController {

    private final ApiKeyService keyService;

    public ApiKeyController(ApiKeyService apiKeyService) {
        keyService = apiKeyService;
    }

    @PostMapping("/{}/generateNew")
    public ApiResponse<ApiKey> generateNew(@RequestParam String ownerId) {
        return keyService.generateNewKey(ownerId);
    }

    @PostMapping("/{apiKey}/deactivate")
    public ApiResponse<Boolean> deactivateKey(@PathVariable String apiKey) {
        return keyService.invalidateApikey(apiKey);
    }

    @PostMapping("/{apiKey}/delete")
    public ApiResponse<Boolean> deleteKey(@PathVariable String apiKey) {
        return keyService.deleteKey(apiKey);
    }

    @GetMapping("/users/{ownerId}/keys")
    public ApiResponse<List<ApiKey>> getUserKeys(@PathVariable String ownerId) {
        return keyService.getUserApikeys(ownerId);
    }
}
