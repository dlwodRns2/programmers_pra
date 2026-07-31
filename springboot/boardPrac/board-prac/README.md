1. basic-board의 세션 기반 로그인을 jwt 토큰 방식으로 변경
=> 서버는 stateless

2. 인가 적용 : 관리자는 모든 게시글을 삭제 가능. BoardAuthService에 boolean canDelete 메서드로 구현 
2-1. 헤더의 Autorization에 토큰을 실어서 Delete /api/boards/boardId 요청
2-2. TokenAuthenticationFilter 통과 : 토큰이 유효한지 확인 후 SecurityContext에 Member 등록.
2-3. authorizeHttpRequests 통과 : 4-2에서 SecurityContext를 채웠으면 통과
2-4. BoardApiController.deleteArtice에 @PreAuthorize("@boardAuthService.canDelete(#id,authentication)") 어노테이션을 추가
2-5. deleteArticle 처리 전에 canDelete 부터 실행. 
2-6. canDelete의 결과가 true : boardService.deleteArticle 실행 -> 게시글 삭제 / false : AccessDeniedException 발생. 403에러

//BoardAuthService

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {
private final BoardRepository boardRepository;
private final FileService fileService;

    // 최신 글이 위로 오도록 id 내림차순. 화면 page(1~)를 Pageable(0~)로 바꾸려 page - 1
    public List<Board> getBoardList(int page,int size){
        Pageable pageable = PageRequest.of(page-1, size, Sort.by("id").descending());
        return boardRepository.findAll(pageable).getContent();

    }
    public int getTotalBoards(){
        return Math.toIntExact(boardRepository.count());
    }

    public Board getBoardDetail(Long id){
        return boardRepository.findById(id)
                .orElseThrow(()->new BoardNotFountException("게시글을 찾을 수 없습니다. id=" + id));
    }

    @Transactional
    public void saveArticle(BoardWriteRequestDto dto, String userId){
        String filePath = fileService.storeFile(dto.getFile());

        Board board  = Board.builder()
                .userId(userId)
                .title(dto.getTitle())
                .content(dto.getContent())
                .filePath(filePath)
                .created(LocalDateTime.now())
                .build();

        boardRepository.save(board);
    }

    @Transactional
    public void deleteArticle(Long id, BoardDeleteRequestDto dto){
        if(!boardRepository.existsById(id)){
            throw new BoardNotFountException("게시글을 찾을 수 없습니다. id="+ id);
        }
        boardRepository.deleteById(id);
        fileService.deleteFile(dto.getFilePath());
    }

    @Transactional
    public void updateArticle(Long id, BoardUpdateRequestDto dto){
        Board board = boardRepository.findById(id)
                .orElseThrow(()->new BoardNotFountException("게시글을 찾을 수 없습니다. id="+id));

        String filePath = board.getFilePath();
        if(dto.isFileFlag()){
            fileService.deleteFile(board.getFilePath());
            filePath= fileService.storeFile(dto.getFile());
        }

        board.update(dto.getTitle(),dto.getContent(),filePath);
    }
}
