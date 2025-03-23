package com.example.weather.service;

import com.example.weather.model.City;
import com.example.weather.model.Weather;
import com.example.weather.model.WeatherApiResponse;
import com.example.weather.repository.WeatherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class WeatherServiceTest {

    @Mock
    private WeatherRepository weatherRepository;

    @InjectMocks
    private WeatherService weatherService;

    // Use a Spy for RestTemplate to simulate external API calls.
    @Spy
    private RestTemplate restTemplate = new RestTemplate();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Optionally, set a test API key if needed:
        // weatherService.apiKey = "TEST_API_KEY";
    }

    @Test
    void testFetchWeatherData() {
        var testCity = new City(123, "test", "test", "test", new City.Coord(1,2));
        // Prepare a simulated response.
        WeatherApiResponse response = new WeatherApiResponse();
        WeatherApiResponse.Main main = new WeatherApiResponse.Main();
        main.setTemp(30.0);
        WeatherApiResponse.Coord coord = new WeatherApiResponse.Coord();
        coord.setLon(10.0);
        coord.setLat(20.0);
        response.setMain(main);
        response.setCoord(coord);

        // Simulate RestTemplate returning our response.
        ResponseEntity<WeatherApiResponse> entity = ResponseEntity.ok(response);
        doReturn(entity).when(restTemplate).getForEntity(anyString(), eq(WeatherApiResponse.class));

        WeatherApiResponse result = weatherService.fetchWeatherData(testCity.getCoord().getLat(), testCity.getCoord().getLon());

        // Validate that the response data is as expected.
        assert(result.getMain().getTemp() == 30.0);
        assert(result.getCoord().getLon() == 10.0);
        assert(result.getCoord().getLat() == 20.0);
    }

    @Test
    void testUpdateWeatherForCities() {
        // Create a sample city.
        City city = new City();
        city.setId(123);
        city.setName("Test City");
        city.setCoord(new City.Coord(1,2));

        // Prepare a simulated API response.
        WeatherApiResponse response = new WeatherApiResponse();
        WeatherApiResponse.Main main = new WeatherApiResponse.Main();
        main.setTemp(25.0);
        WeatherApiResponse.Coord coord = new WeatherApiResponse.Coord();
        coord.setLon(15.0);
        coord.setLat(30.0);
        response.setMain(main);
        response.setCoord(coord);

        ResponseEntity<WeatherApiResponse> entity = ResponseEntity.ok(response);
        doReturn(entity).when(restTemplate).getForEntity(anyString(), eq(WeatherApiResponse.class));

        // Simulate that no Weather record exists for this city.
        when(weatherRepository.findByCityId(city.getId())).thenReturn(Optional.empty());

        // Invoke updateWeatherForCities.
        weatherService.updateWeatherForCities(List.of(city));

        // Verify that the repository's save method is called once.
        verify(weatherRepository, times(1)).save(any(Weather.class));
    }
}
