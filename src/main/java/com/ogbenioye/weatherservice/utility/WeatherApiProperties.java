package com.ogbenioye.weatherservice.utility;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter @Setter
@ConfigurationProperties(prefix = "weather.api")
public class WeatherApiProperties {
    private String key;
    private String baseUrl;
}
