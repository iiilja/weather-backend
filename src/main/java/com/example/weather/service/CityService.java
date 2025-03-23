package com.example.weather.service;

import com.example.weather.model.City;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class CityService {
    private static final Logger logger = LoggerFactory.getLogger(CityService.class);

    public List<City> getCities()  {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = getClass().getResourceAsStream("/city_list.json");
        if (is == null) {
            throw new RuntimeException("File city_list.json not found in resources!");
        }
        try {
            return mapper.readValue(is, new TypeReference<List<City>>() {});
        } catch (IOException e) {
            logger.error("Error reading cities", e);
            return Collections.emptyList();
        }
    }
}
