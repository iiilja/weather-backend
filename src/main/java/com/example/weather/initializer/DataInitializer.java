package com.example.weather.initializer;

import com.example.weather.model.City;
import com.example.weather.repository.WeatherRepository;
import com.example.weather.service.CityService;
import com.example.weather.service.WeatherService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataInitializer implements ApplicationRunner {

    private final WeatherRepository weatherRepository;
    private final WeatherService weatherService;
    private final CityService cityService;

    public DataInitializer(WeatherRepository weatherRepository, WeatherService weatherService, CityService cityService) {
        this.weatherRepository = weatherRepository;
        this.weatherService = weatherService;
        this.cityService = cityService;
    }

    @Override
    public void run(ApplicationArguments args) {
        weatherRepository.deleteAll();
        var cities = cityService.getCities();
        List<City> limitedCities = cities.stream().limit(20).collect(Collectors.toList());
        weatherService.updateWeatherForCities(limitedCities);
    }
}
