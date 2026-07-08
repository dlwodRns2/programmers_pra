package org.example.basicboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@RequestBody
//컨트롤러에서 @RequestBody로 값을 받으면,
//JSON을 Jackson 라이브러리가 이 Dto 객체로 바꾼다(역직렬화)
//Jackson은 setter로 값을 채움 => Setter 추가
@Getter
@Setter
@NoArgsConstructor
public class BoardDeleteRequestDto {
    private String filePath;
}
