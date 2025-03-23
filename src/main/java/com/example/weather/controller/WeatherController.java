package com.example.weather.controller;

import com.example.weather.model.Weather;
import com.example.weather.repository.WeatherRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/temperatures")
public class WeatherController {

    private final WeatherRepository weatherRepository;

    public WeatherController(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    /**
     * Retrieves a paginated list of weather records.
     *
     * @param page      the page number (default: 0)
     * @param size      the page size (default: 5)
     * @param sortBy    the field to sort by (default: cityName)
     * @param direction the sort direction ("asc" or "desc", default: asc)
     * @return a Page of Weather records
     */
    @GetMapping
    public Page<Weather> getWeather(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "cityName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        return weatherRepository.findAll(PageRequest.of(page, size, sort));
    }
}
