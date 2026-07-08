package org.example.basicboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class BoardUpdateRequestDto {
    private String title;
    private String content;
    private MultipartFile file; //새로 올린 파일(변경했을 경우에만 값이 존재)
    private boolean fileFlag; //첨부파일을 변경했는지 여부
}
