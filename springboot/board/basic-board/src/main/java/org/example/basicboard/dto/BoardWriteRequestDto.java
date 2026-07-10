package org.example.basicboard.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

//* @ModelAttribute
//form 필드가 같은 이름의 필드에 자동으로 채워진다.
//파일은 JSON에 실을 수 없음 -> multiPart + @ModelAttribute를 통해 실어 나른다.
//그래서 아래 필드 이름은 화면 폼의 name 속성과 똑같아야함.

//@Setter / @NoArgsConstructor를 추가한 이유
//@ModelAttribute는 "기본 생성자로 객체를 만든 뒤, Setter로 값을 하나씩 채우는 방식"
//그래서 응답 DTO들처럼 @Builder
@Getter
@Setter
@NoArgsConstructor
public class BoardWriteRequestDto {
    @Schema(description = "게시글 제목", example = "첫 글입니다.")
    private String title;
    @Schema(description = "게시글 내용",example = "안녕하세요. 반갑습니다.")
    private String content;
    @Schema(description = "작성자 아이디(로그인 사용자)", example = "user01")
    private String userId;

    //MultipartFile
    //multiPart/form-date로 업로드 된 "파일 1개" 를 스프링이 감싸서 넣어주는 타입
    // 파일의 바이트뿐 아니라 메타데이터도 함께 들고 있다. 자주 쓰는 메서드:
    //     getOriginalFilename() : 업로드된 원본 파일명 (예: "고양이.png")
    //     getContentType()      : MIME 타입 (예: "image/png")
    //     getSize()             : 파일 크기(byte)
    //     isEmpty()             : 파일을 안 골랐거나 빈 파일이면 true
    //     getInputStream()      : 내용을 읽는 스트림
    //     transferTo(dest)      : 실제 디스크 경로로 저장
    @Schema(description = "첨부파일(선택). 안고르면 비어있음",type="string",format = "binary")
    private MultipartFile file;
}
