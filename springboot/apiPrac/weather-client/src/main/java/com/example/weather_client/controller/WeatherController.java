package com.example.weather_client.controller;

import com.example.weather_client.dto.Item;
import com.example.weather_client.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping("/weather")
    public List<String> weather(){
        //return weatherService.getCurrentWeather(60,127);
        return weatherService.getReadableWeather(60,127);
    }
}
