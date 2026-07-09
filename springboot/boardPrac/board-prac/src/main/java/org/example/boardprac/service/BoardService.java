package org.example.boardprac.service;

import lombok.RequiredArgsConstructor;
import org.example.boardprac.domain.entity.Board;
import org.example.boardprac.domain.repository.BoardRepository;
import org.example.boardprac.domain.repository.MemberRepository;
import org.example.boardprac.dto.BoardWriteRequestDto;
import org.example.boardprac.exception.BoardNotFountException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

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
    public String storeFile(MultipartFile file){
            if(file==null||file.isEmpty()){
                return null;
            }
            try{
                File dir = new File(uploadDir).getAbsoluteFile();
                if(!dir.exists()){
                    dir.mkdirs();
                }

                String storedName = UUID.randomUUID()+"_"+file.getOriginalFilename();
                File dest = new File(dir,storedName);

                file.transferTo(dest);
                return dest.getPath();
            } catch (IOException e) {
                throw new IllegalStateException("파일 저장에 실패했습니다.",e);
            }
    }

    @Transactional
    public void saveArticle(BoardWriteRequestDto dto){
        String filePath = storeFile(dto.getFile());

        Board board  = Board.builder()
                .userId(dto.getUserId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .filePath(filePath)
                .created(LocalDateTime.now())
                .build();

        boardRepository.save(board);
    }

    public Resource downloadFile(String fileName){
        File file = new File(new File(uploadDir).getAbsoluteFile(), fileName);
        try {
            Resource resource = new UrlResource(file.toURI());
            if(!resource.exists()){
                throw new BoardNotFountException("파일을 찾을 수 없습니다. fileName = "+fileName);
            }
            return resource;

        } catch (MalformedURLException e) {
            throw new IllegalStateException("파일 경로가 잘못되었습니다."+e);
        }
    }



}
