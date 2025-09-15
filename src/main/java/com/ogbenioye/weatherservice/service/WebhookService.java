package com.ogbenioye.weatherservice.service;

import com.ogbenioye.weatherservice.dto.WeatherResponse;
import com.ogbenioye.weatherservice.repository.ApiKeyRepository;
import com.ogbenioye.weatherservice.repository.ApplicationUserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@AllArgsConstructor
public class WebhookService {

    private final RestTemplate restTemplate = new RestTemplate();
        private final ApiKeyRepository apiKeyRepository;

    public void sendReport(String webhookUrl, List<WeatherResponse> weatherReport) {
        if (webhookUrl == null || webhookUrl.isBlank()) {
            return; // user has no webhook
        }

        try {
            restTemplate.postForEntity(webhookUrl, weatherReport, String.class);
        } catch (Exception e) {
            // todo: log failure & implement retry logic
            System.err.println("Failed to send webhook: " + e.getMessage());
        }
    }

    public String getWebhookUrl(String key) {
        var apiKey = apiKeyRepository.findByApiKey(key);
        if (apiKey.isEmpty()) {
            return "";
        }
        return apiKey.get().getOwner().getWebhook();
    }
}
