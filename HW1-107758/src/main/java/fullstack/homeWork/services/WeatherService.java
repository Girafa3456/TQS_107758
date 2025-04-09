package fullstack.homeWork.services;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WeatherService {
    // location defined here (set for Aveiro)
    private static final String API_URL = "https://api.open-meteo.com/v1/forecast?latitude=40.64&longitude=-8.65&daily=weathercode,temperature_2m_max,temperature_2m_min&timezone=auto";
    private final RestTemplate restTemplate = new RestTemplate();

    @Cacheable(value = "weatherForecasts", key = "#date.toString()")
    public Map<String, Object> getWeatherForDate(LocalDate date) {
        log.info("Fetching weather forecast for date: {}", date);
        
        try {
            log.debug("Making API call to Open-Meteo: {}", API_URL);
            Map<String, Object> response = restTemplate.getForObject(API_URL, Map.class);
            
            if (response == null || !response.containsKey("daily")) {
                log.error("Invalid API response structure received");
                return null;
            }

            Map<String, Object> daily = (Map<String, Object>) response.get("daily");
            log.debug("Received daily forecast data for {} days", ((List<?>) daily.get("time")).size());

            // Find the index for our date
            int index = ((List<String>) daily.get("time")).indexOf(date.toString());
            
            if (index == -1) {
                log.warn("Requested date {} not found in forecast data", date);
                return null;
            }

            Map<String, Object> weatherData = new HashMap<>();
            weatherData.put("weatherCode", ((List<Integer>) daily.get("weathercode")).get(index));
            weatherData.put("tempMax", ((List<Double>) daily.get("temperature_2m_max")).get(index));
            weatherData.put("tempMin", ((List<Double>) daily.get("temperature_2m_min")).get(index));
            
            log.info("Successfully retrieved weather for {}: {}°C/{}°C (code: {})", 
                date, 
                weatherData.get("tempMax"), 
                weatherData.get("tempMin"), 
                weatherData.get("weatherCode"));
                
            return weatherData;
        } catch (Exception e) {
            log.error("Error fetching weather for date {}: {}", date, e.getMessage(), e);
            return null;
        }
    }

    public String getWeatherIcon(int weatherCode) {
        log.debug("Getting weather icon for code: {}", weatherCode);
        
        String icon;
        if (weatherCode == 0) {
            icon = "☀️"; // Clear sky
        } else if (weatherCode <= 3) {
            icon = "⛅"; // Partly cloudy
        } else if (weatherCode <= 48) {
            icon = "🌫️"; // Fog
        } else if (weatherCode <= 67) {
            icon = "🌧️"; // Rain
        } else if (weatherCode <= 77) {
            icon = "❄️"; // Snow
        } else if (weatherCode <= 99) {
            icon = "⛈️"; // Thunderstorm
        } else {
            icon = "🌈";
        }
        
        log.trace("Mapped weather code {} to icon {}", weatherCode, icon);
        return icon;
    }
}