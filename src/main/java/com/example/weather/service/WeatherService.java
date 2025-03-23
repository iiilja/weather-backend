package com.example.weather.service;

import com.example.weather.model.City;
import com.example.weather.model.Weather;
import com.example.weather.model.WeatherApiResponse;
import com.example.weather.repository.WeatherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
public class WeatherService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    private final WeatherRepository weatherRepository;
    private final RestTemplate restTemplate;

    @Value("${weather.api.key:MY_WEATHER_API_KEY}")
    private String apiKey;

    public WeatherService(WeatherRepository weatherRepository, RestTemplate restTemplate) {
        this.weatherRepository = weatherRepository;
        this.restTemplate = restTemplate;
    }

    @Retryable(value = {RestClientException.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public WeatherApiResponse fetchWeatherData(double lat, double lon) {
        String url = "http://api.openweathermap.org/data/2.5/weather" +
                "?lat=" + lat +
                "&lon=" + lon +
                "&units=metric" +
                "&appid=" + apiKey;
        logger.debug("Fetching weather data for city lat {}, lon {} from URL: {}", lat, lon, url);
        ResponseEntity<WeatherApiResponse> response = restTemplate.getForEntity(url, WeatherApiResponse.class);
        WeatherApiResponse weatherResponse = response.getBody();
        logger.debug("Fetched weather data for city lat {}, lon {}: {}", lat, lon, weatherResponse);
        return weatherResponse;
    }

    public void updateWeather() {
        var cities = weatherRepository.findAll();
        for (Weather weather : cities) {
            try {
                WeatherApiResponse weatherResponse = fetchWeatherData(weather.getLat(), weather.getLon());
                Double temp = weatherResponse.getMain().getTemp();
                double lon = weatherResponse.getCoord().getLon();
                double lat = weatherResponse.getCoord().getLat();
                weather.setTemperature(temp);
                weatherRepository.save(weather);
                logger.info("Updated weather for city: {} with temperature: {}, lon: {}, lat: {}",
                        weather.getCityName(), temp, lon, lat);
            } catch (Exception e) {
                logger.error("Error fetching data for city: {}", weather.getCityName(), e);
            }
        }
    }

    public void updateWeatherForCities(List<City> cities) {
        for (City city : cities) {
            try {
                WeatherApiResponse weatherResponse = fetchWeatherData(city.getCoord().getLat(), city.getCoord().getLon());
                Double temp = weatherResponse.getMain().getTemp();
                double lon = weatherResponse.getCoord().getLon();
                double lat = weatherResponse.getCoord().getLat();
                Weather weather = weatherRepository.findByCityId(city.getId())
                        .orElse(new Weather(city.getId(), city.getName(), temp, lon, lat));
                weather.setTemperature(temp);
                weatherRepository.save(weather);
                logger.info("Updated weather for city: {} with temperature: {}, lon: {}, lat: {}",
                        city.getName(), temp, lon, lat);
            } catch (Exception e) {
                logger.error("Error fetching data for city: {}", city.getName(), e);
            }
        }
    }
}
