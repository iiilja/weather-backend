package com.example.weather.scheduler;

import com.example.weather.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WeatherUpdateScheduler {

    private static final Logger logger = LoggerFactory.getLogger(WeatherUpdateScheduler.class);
    private final WeatherService weatherService;

    public WeatherUpdateScheduler(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Scheduled(cron = "0 0 3 * * ?")
    public void scheduledWeatherUpdate() {
        logger.info("Scheduled weather update started.");
        weatherService.updateWeather();
        logger.info("Scheduled weather update completed successfully.");
    }
}
