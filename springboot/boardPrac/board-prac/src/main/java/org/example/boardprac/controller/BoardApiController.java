package org.example.boardprac.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.boardprac.domain.entity.Board;
import org.example.boardprac.dto.*;
import org.example.boardprac.service.BoardService;
import org.example.boardprac.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Tag(name="게시글 API",description = "게시글 목록/상세 조회, 작성, 수정, 삭제, 첨부파일 다운로드")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardApiController {
    private final BoardService boardService;
    private final FileService fileService;

    @Operation(
            summary = "게시글 목록 조회",
            description = "페이지 단위로 게시글 목록을 조회한다. 목록(boards)과 마지막 페이지 여부(last), 전체 페이지 수(totalPages)를 함께 돌려준다."
    )
    @GetMapping
    public BoardListResponseDto getBoardList(
            @Parameter(description = "조회할 페이지 번호 (1부터 시작)",example="1")
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "한 페이지에 담을 게시글 수",example="10")
            @RequestParam(defaultValue = "10") int size){
        List<Board> boards = boardService.getBoardList(page,size);
        int totalBoards = boardService.getTotalBoards();

        int totalPages = (int) Math.ceil((double) totalBoards /size);
        boolean last = totalPages<=page;

        return BoardListResponseDto.builder()
                .boards(boards)
                .last(last)
                .totalPages(totalPages)
                .build();
    }

    //@ApiResponse : "이 API가 낼 수 있는 모든 응답들"을 상태코드별로 문서에 나열
    //- 성공(200)만이 아니라, 실패(404)도 미리 적어두면, 이 API를 쓰는 사람이 어떤 상황을 대비해야 하는지 한 눈에 알 수 있음
    //- 404의 응답 본문 형태(Schema)를 ErrorResponseDto로 지정하면, 실패 시 무엇이 오는지까지 문서에 드러난다
    @Operation(
            summary = "게시글 상세 조회",
            description = "id로 게시글 한 건의 상세 내용을 조회한다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description ="게시글 상세 조회 성공" ),
            @ApiResponse(responseCode = "404", description = "게시글 상세 조회 실패 - 없음",
                    content = @Content(schema = @Schema(implementation =  ErrorResponseDto.class))
            )
    })
    @GetMapping("/{id}")
    public BoardDetailResponseDto getBoardDetail(@PathVariable Long id){
        Board board = boardService.getBoardDetail(id);
        return BoardDetailResponseDto.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .userId(board.getUserId())
                .created(board.getCreated())
                .filePath(board.getFilePath())
                .build();
    }

    @Operation(summary = "첨부파일 다운로드",
            description = "저장된 파일 이름으로 첨부파일을 내려받는다. Content-Disposition: attachment 로 브라우저가 다운로드하게 한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "파일 다운로드",
                    content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE,
                            schema = @Schema(type = "string", format = "binary"))),
            @ApiResponse(responseCode = "404", description = "해당 이름의 파일이 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/file/download/{fileName}")
    public ResponseEntity<Resource> download(
            @Parameter(description = "서버에 저장된 파일 이름(UUID 포함)", example = "3f2a1b_이력서.pdf")
            @PathVariable String fileName){
        Resource resource = fileService.downloadFile(fileName);

        String encoded = URLEncoder.encode(resource.getFilename(), StandardCharsets.UTF_8)
                .replaceAll("\\+","%20");

        //httpStatus : ok, body : resource 담기
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename*=UTF-8''"+encoded)
                .body(resource);
    }
    @Operation(
            summary = "게시글 작성",
            description = "제목/내용/작성자와 (선택적) 첨부파일을 multipart/form-data 로 받아 새 게시글을 저장한다."
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveArticle(@ModelAttribute BoardWriteRequestDto dto){
        boardService.saveArticle(dto);
    }

    @Operation(summary = "게시글 삭제",
            description = "경로의 id 게시글을 삭제한다. 첨부파일 경로(filePath)를 JSON 본문으로 함께 받아 파일도 정리한다.")
    @DeleteMapping("/{id}")
    public void deleteArticle(
            @Parameter(description = "삭제할 게시글 id", example = "1")
            @PathVariable Long id,
            @RequestBody BoardDeleteRequestDto dto
            ){
        boardService.deleteArticle(id, dto);
    }

    @Operation(summary = "게시글 수정",
            description = "경로의 id 게시글을 수정한다. 파일 교체가 가능하도록 multipart/form-data 로 받는다.")
    @PutMapping("/{id}")
    public void updateArticle(
            @Parameter(description = "수정할 게시글 id", example = "1")
            @PathVariable Long id,
            @ModelAttribute BoardUpdateRequestDto dto
            ){
        boardService.updateArticle(id,dto);
    }
}
