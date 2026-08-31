package org.example.boardservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.boardservice.domain.repository.BoardRepository;
import org.example.boardservice.domain.repository.BoardRepositoryCustom;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public void searchBoards(){
        // searchBoards 게시글들 가져오기

        // boardRepository에서 가져온 ID추려서 auth-service로 요청해서 userName들 받아오기
    }
}
