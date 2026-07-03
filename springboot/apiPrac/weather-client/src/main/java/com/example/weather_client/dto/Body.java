package com.example.weather_client.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Body{
    private String dataType;
    private Items items;
    private int numOfRows;
    private int pageNo;
    private int totalCount;
}