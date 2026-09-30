package org.example.webservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.webservice.client.BoardClient;
import org.example.webservice.dto.CommentWriteRequestDto;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final BoardClient boardClient;

    public void addComment(
            String authorization,
            long boardId,
            CommentWriteRequestDto requestDto
    ){
        boardClient.addComment(authorization, boardId, requestDto);
    }

}
