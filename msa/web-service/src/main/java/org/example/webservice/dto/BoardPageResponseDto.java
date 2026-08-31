package org.example.webservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardPageResponseDto {
    //이번 페이지의 게시글 목록(Page의 표준 필드명 유지 - 프론트가 response.content로 읽음)
    private List<BoardListItemResponseDto> content;
    //전체 페이지 수(페이지 버튼 렌더링에 사용)
    private int totalPage;
    //전체 게시글 수
    private long totalElements;
    //현재 페이지 번호(0부터)
    private int number;
    //페이지 크기
    private int size;
    private boolean first;
    private boolean last;
    private boolean empty;
}
