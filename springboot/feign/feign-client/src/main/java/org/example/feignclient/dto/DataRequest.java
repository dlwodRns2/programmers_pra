package org.example.feignclient.dto;

import lombok.Builder;
import lombok.Getter;

// * DTO(Data Transfer Object) : 데이터 전송용 객체
// 계층 간 또는 섭저 간에 데이터를 담아 주고받을 때 사용
// (POST 등으로 요청 본문(body)에 데이터를 실어 보낼 때 활용된다.)
@Getter
@Builder
public class DataRequest {
    private String name;
    private int value;
}
