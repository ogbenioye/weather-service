package com.ogbenioye.weatherservice.service;

import com.ogbenioye.weatherservice.dto.ApiResponse;
import com.ogbenioye.weatherservice.dto.WeatherFilterDto;
import com.ogbenioye.weatherservice.dto.WeatherResponse;
import com.ogbenioye.weatherservice.model.WeatherReport;
import com.ogbenioye.weatherservice.repository.ApiKeyRepository;
import com.ogbenioye.weatherservice.repository.RegionRepository;
import com.ogbenioye.weatherservice.repository.WeatherRepository;
import com.ogbenioye.weatherservice.utility.WeatherApiProperties;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class WeatherService {
    private final WeatherApiProperties properties;
    private final WebClient webclient;
    private final RegionRepository regionRepo;
    private final WeatherRepository weatherRepo;
    private final WebhookService webhookService;
    private final HttpServletRequest request;
    private final ApiKeyRepository apiKeyRepository;

    public WeatherService(
            WeatherApiProperties properties, RegionRepository regionRepo,
            WeatherRepository weatherRepo, WebhookService webhookService,
            HttpServletRequest request, ApiKeyRepository  apiKeyRepository) {
        this.properties = properties;
        this.webclient = WebClient.create(properties.getBaseUrl());
        this.regionRepo = regionRepo;
        this.weatherRepo = weatherRepo;
        this.webhookService = webhookService;
        this.request = request;
        this.apiKeyRepository = apiKeyRepository;
    }

    //crawl api and save to db
    @Scheduled(cron = "0 0 19 * * ?")
    public void  getDailyReport() {
        var regions = regionRepo.findAll();
        for (var region : regions) {
            var response = webclient.get()
                    .uri(
                            "/onecall?lat={lat}&lon={lon}&appid={apiKey}",
                            region.getLatitude(),
                            region.getLongitude(), properties.getKey()
                    )
                    .retrieve().bodyToMono(WeatherReport.class).block();

            if (response != null) {
                //find region in db and update
                var report = weatherRepo.findByRegion(region.getRegion());
                if (report.isPresent()) {
                    report.get().getDaily().addAll(response.getDaily());
                    weatherRepo.save(report.get());
                } else {
                    response.setRegion(region.getRegion());
                    weatherRepo.save(response);
                }

            }

            //log success or error message
        }

        //log final message; eg = report from <region> saved, report from <region> failed, etc
    }

    //get report with filters
    public ApiResponse<List<WeatherResponse>> getWeatherReport(WeatherFilterDto filter) {

        List<WeatherReport> reports = new ArrayList<>();
        if(filter.getRegionName() == null) {
            reports = weatherRepo.findAll();
        } else {
            reports = weatherRepo.findAllByRegionIgnoreCase(filter.getRegionName());
        }

        var from = filter.getFromDate();
        var to = filter.getToDate();

        List<WeatherResponse> weatherReports = new ArrayList<>();

        if (from != null && to != null) {
            reports.forEach(report -> {
                var dailyReports = report.getDaily().stream()
                        .filter(x -> x.getDate().isBefore(from) && x.getDate().isAfter(to)).toList();

                var dto = new WeatherResponse(
                        report.getRegion(),
                        dailyReports
                );

                weatherReports.add(dto);
            });
        } else {
            reports.forEach(report -> {
                var dailyReports = report.getDaily();

                var dto = new WeatherResponse(
                        report.getRegion(),
                        dailyReports
                );

                weatherReports.add(dto);
            });
        }

        //TODO: Abstract
        var key = request.getHeader("X-API-KEY");
        var apiKey = apiKeyRepository.findByApiKey(key);

        // trigger webhook
        var webhookUrl = webhookService.getWebhookUrl(apiKey.get().getApiKey());
        CompletableFuture.runAsync(() -> webhookService.sendReport(webhookUrl, weatherReports));

        return new ApiResponse<>(
                true,
                "Retrieved Successfully",
                HttpStatus.OK,
                weatherReports
        );
    }
}
