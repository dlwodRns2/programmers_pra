package org.example.basicboard.controller;

import lombok.RequiredArgsConstructor;
import org.example.basicboard.domain.entity.Board;
import org.example.basicboard.dto.*;
import org.example.basicboard.service.BoardService;
import org.example.basicboard.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardApiController {
    private final BoardService boardService;
    private final FileService fileService;

    @GetMapping
    public BoardListResponseDto getBoardList(
            @RequestParam(defaultValue = "1")int page,
            @RequestParam(defaultValue = "10") int size
    ){
        List<Board> boards = boardService.getBoardList(page,size);
        int totalBoards = boardService.getTotalBoards();
        int totalPages = (int)Math.ceil((double)totalBoards/size);
        boolean last = page>=totalPages;

        return BoardListResponseDto.builder()
                .boards(boards)
                .last(last)
                .totalPages(totalPages)
                .build();
    }
    @GetMapping("/{id}")
    public BoardDetailResponseDto getBoardDetail(@PathVariable Long id){
        Board board = boardService.getBoardDetail(id);
        return BoardDetailResponseDto.builder()
                .userId(board.getUserId())
                .title(board.getTitle())
                .content(board.getContent())
                .created(board.getCreated())
                .filePath(board.getFilePath())
                .build();

    }

    @PostMapping
    public void saveBoard(@ModelAttribute BoardWriteRequestDto dto){
        boardService.saveBoard(dto);
    }
    //ResponseEntity는 Http 응답의 3가지를 직접 제어하게 해주는 상자
    //[상태코드] + [헤더] + [본문(body)]의 제어 필요 시, 사용
    //그냥 Resource만 반환하면 파일 내용은 내려가지만,
    //Content-Disposition: attachment 헤더를 붙일 방법이 없음
    //-> 붙이지 않으면 다운로드가 아니라, 파일을 그냥 열어버리고 저장 파일명도 지정 불가
    @GetMapping("/file/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName){
        Resource resource = fileService.downloadFile(fileName);

        //* 한글 파일명 인코딩
        //HTTP 헤더 값에는 원칙적으로 ASCII만 안전하게 담을 수 있음
        //-> "이력서.pdf" 같은 한글/공백을 그대로 넣으면 깨지거나 잘림
        //그래서 파일명을 URL 인코딩해서 넣는다
        //URLEncoder는 공백을 '+'로 바꾸는데,
        //파일명에서는 '+'가 그대로 보이면 곤란하므로 %20로 치환.
        String encodedFileName = URLEncoder.encode(resource.getFilename(), StandardCharsets.UTF_8);

        //* Content Type(MediaType.APPLICATION_OCTET_STREAM) => 힌트
        //무슨 파일인지 특정하지 않은 순수 바이너리 라는 뜻
        //브라우저가 열 방법을 몰라 저장 쪽으로 기울게 하는 '힌트'일 뿐, 다운로드로 확정하지는 못함
        //(확장자 등에 따라 브라우저가 그냥 열어버릴 수 있음)

        //* .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFileName)
        //attachment는 인라인으로 열지 말고 무조건 첨부(다운로드)하라는 확실한 지시
        //filename* 는 인코딩을 명시하는 최신 문법, "저장될 기본 파일명"을 지정
        //이게 없으면 URL 끝의 UUID 붙음 이름으로 저장되어 버림. 그래서 원본 이름으로 저장되게끔 넣는 것
        //utf8 뒤에 '' : 언어 필드 생략(ex: utf8'ko'
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''"+encodedFileName)
                .body(resource);
    }

    @PutMapping("/{id}")
    public void updateBoard(@PathVariable Long id,
                            @ModelAttribute BoardUpdateRequestDto dto){
        boardService.updateBoard(id,dto);
    }
    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable Long id, @RequestBody BoardDeleteRequestDto dto){
        boardService.deleteBoard(id,dto);
    }
}
