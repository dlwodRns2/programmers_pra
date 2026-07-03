package com.example.weather_client.service;

import com.example.weather_client.client.WeatherClient;
import com.example.weather_client.dto.Header;
import com.example.weather_client.dto.Item;
import com.example.weather_client.dto.WeatherResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final WeatherClient weatherClient;
    @Value("${weather.api.key}") String serviceKey;

    public List<Item> getCurrentWeather(int nx , int ny){
        LocalDateTime now = LocalDateTime.now();
        if(now.getMinute()<40){
            now=now.minusHours(1);
        }

        String baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String baseTime = now.format(DateTimeFormatter.ofPattern("HH"))+"00";

        WeatherResponse res = weatherClient.getUltraSrtNcst(
                serviceKey, 10, 1, "JSON", baseDate, baseTime,nx,ny
        );

        Header header = res.getResponse().getHeader();

        if(!"00".equals(header.getResultCode())){
            throw new RuntimeException("기상청 API 오류: "
            +header.getResultCode()+" "+header.getResultMsg());
        }

        return res.getResponse().getBody().getItems().getItem();
    }
    public List<String> getReadableWeather(int nx,int ny){
        List<Item> items = getCurrentWeather(nx,ny);
        List<String> result = new ArrayList<>();

        for(Item item : items){
            String category = item.getCategory();
            String value = item.getObsrValue();

            switch(category){
                case "T1H" -> result.add("기온: "+value+" ℃");
                case "REH"->result.add("습도: "+value+" %");
                case "RN1"->result.add("1시간 강수량: "+value+" mm");
                case "WSD"->result.add("풍속: "+value+" m/s");
                case "PTY" ->result.add("강수형태: "+ptyText(value));
            }
        }
        return result;
    }
    private String ptyText(String code){
            return switch (code){
                case "0" -> "없음";
                case "1"->"비";
                case "2" ->"비/눈";
                case "3" -> "눈";
                case "5" -> "빗방울";
                case "6" -> "빗방울눈날림";
                case "7" ->"눈날림";
                default -> "알 수 없음("+code+")";
            };
    }
}
