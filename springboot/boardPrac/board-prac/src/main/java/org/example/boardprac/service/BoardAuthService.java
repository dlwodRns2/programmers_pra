package org.example.boardprac.service;

import lombok.RequiredArgsConstructor;
import org.example.boardprac.config.security.CustomUserDetails;
import org.example.boardprac.domain.entity.Board;
import org.example.boardprac.domain.entity.Member;
import org.example.boardprac.domain.repository.BoardRepository;
import org.example.boardprac.domain.repository.MemberRepository;
import org.example.boardprac.exception.BoardNotFountException;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardAuthService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final RoleHierarchy roleHierarchy;

    //삭제 요청자 권한 / 게시글 작성자 권한을 비교해서 삭제 가능 여부를 판단하는 메서드
    public boolean canDelete(Long boardId, Authentication authentication){
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()->new BoardNotFountException("게시글을 찾을 수 없습니다."));

        //삭제 요청자
        CustomUserDetails requester = (CustomUserDetails) authentication.getPrincipal();

        //자기가 작성한 글이면 ROLE 관계없이 삭제
        if(board.getUserId().equals(requester.getUsername())){
            return true;
        }

        //삭제 요청자가 admin인지 확인.
        boolean isAdmin = requester.getAuthorities().stream()
                .anyMatch(a-> a.getAuthority().equals("ROLE_ADMIN"));

        //admin이 아니면 false 반환
        if(!isAdmin){
            return false;
        }

        //게시글 작성자 확인
        Member author = memberRepository.findByUserId(board.getUserId())
                .orElseThrow(()->new BoardNotFountException("작성자를 찾을 수 없습니다."));

        //삭제 요청자 권한 >= 게시글 작성자의 권한이면 true / 아니면 false
        return roleHierarchy.getReachableGrantedAuthorities(requester.getAuthorities()).stream()
                .anyMatch(a -> a.getAuthority().equals(author.getRole().name()));

    }
}
