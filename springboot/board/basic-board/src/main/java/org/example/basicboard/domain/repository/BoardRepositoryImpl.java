package org.example.basicboard.domain.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.basicboard.domain.entity.Board;
import org.example.basicboard.domain.entity.QBoard;
import org.example.basicboard.domain.entity.QComment;
import org.example.basicboard.domain.entity.QMember;
import org.example.basicboard.dto.BoardListItemResponseDto;
import org.example.basicboard.dto.BoardListResponseDto;
import org.example.basicboard.dto.BoardSearchRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    private static final QBoard board = QBoard.board;
    private static final QComment comment = QComment.comment;
    private static final QMember member = QMember.member;

    //* Projections.constructor
    //- "이 클래스의 생성자를 리플렉션으로 찾아서 끼워 맞추라"는 의미
    //- 클래스 이름과 인자를 "나중에(실행 떼)"끼워 맞추므로, 컴파일 시점에 검사가 불가능
    //-> 인자 순서/타입이 생성자와 어긋나면 컴파일은 통과하고 "실행 시"에 터진다(런타임 오류)
    //- 대신 DTO는 QueryDSL을 전혀 모른다(순수한 DTO 상태 유지)
    @Override
    public Page<BoardListItemResponseDto> searchBoards(BoardSearchRequestDto condition, Pageable pageable) {
        List<BoardListItemResponseDto> content = queryFactory.select(
                        Projections.constructor(
                                BoardListItemResponseDto.class,
                                board.id,
                                board.title,
                                board.userId,
                                member.userName,
                                commentCountOf(board),
                                board.created
                        )
                )
                .from(board)
                .leftJoin(member).on(board.userId.eq(member.userId))
                .where(
                        titleContains(condition.getTitle()),
                        userIdEquals(condition.getUserId()),
                        createdGoe(condition.getFrom()),
                        createdLoe(condition.getTo())
                )
                .orderBy(board.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.
                select(board.count())
                .from(board)
                .where(
                        titleContains(condition.getTitle()),
                        userIdEquals(condition.getUserId()),
                        createdGoe(condition.getFrom()),
                        createdLoe(condition.getTo())
                );
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    //만약 그냥 board 하나만 조회한 뒤, board.getComments()를 순회하면?
    //- comments는 Lazy라, 순회하는 순간 "댓글을 가져오는 SQL이 추가로" 나간다.
    //- 문제점 : 게시글이 여러 개면, 게시글마다 댓글 쿼리가 또 나가서 총 1+N번 쿼리가 나감(N+1 문제)
    //- N+1
    //- 1 : 처음 의도하고 날린 쿼리 1번
    //- N : 그 결과 행 수만큼 "추가로" 나가는 N번(게시글마다 댓글 조회 한 번씩)

    //* 실제 SQL 흐름(게시글 100개라고 가정)
    //List<Board> boards = boardRepository.findAll();
    //for(Board board : boards){
    //     board.getComments();
    //}
    //select * from comments where board_id = 2
    //select * from comments where board_id = 1
    //...
    //select * from comments where board_id = 100
    //=> DB에는 총 101번의 쿼리가 나간다(매우 큰 성능 저하)

    //참고 : Lazy라서 생기는 문제는 아님
    //- Eager로 바꿔도 N+1은 그대로이다. 추가 쿼리가 "순회할 때"가 아니라 "조회 직후"에 나갈 뿐
    //- 근본 원인은 "연관 데이터를 행마다 따로 가져오는 것" -> fetch join으로 해결

    //* fetchJoin()을 붙이면, 게시글과 댓글을 "조인해서 한 방에" 가져와 이 문제를 해결한다.
    //- leftJoin(board.comments, comment) : 매핑된 연관관계를 따라가는 조인
    //- fetchJoin() : "조인한 comment를 board.comments에 즉시 채워라"는 지시
    @Override
    public Optional<Board> findWithComments(Long id) {
        Board result = queryFactory
                .selectFrom(board)
                .leftJoin(board.comments, comment).fetchJoin()
                .where(board.id.eq(id))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    // 제목 부분 일치 (Like %title%). 빈 값이면 조건 없음(null)
    private BooleanExpression titleContains(String title) {
        return (title == null || title.isBlank()) ? null : board.title.contains(title);
    }

    // 작성자 아이디 정확히 일치. 빈 값이면 조건 없음(null)
    private BooleanExpression userIdEquals(String userId) {
        return (userId == null || userId.isBlank()) ? null : board.userId.eq(userId);
    }

    //* goe / loe 는 비교연산자의 약어
    // -gt(Greater Than, >)
    // -goe(Greater Than or Equal, >=)
    // -lt(Less Than, <)
    // -loe(Less Than or Equal, <=)
    // -> 아래 goe + loe 한 쌍이 "from 이상 AND to 이하" => Between 기간 검색이 됨
    private BooleanExpression createdGoe(LocalDate from){
        return from ==null ?null : board.created.goe(from.atStartOfDay());
    }
    private BooleanExpression createdLoe(LocalDate to){
        return to ==null ?null : board.created.loe(to.atTime(LocalTime.MAX));
    }
    //JPAQueryFactory와 JPAExpressions
    //- JPAQueryFactory
    //- JPAExpressions
    private Expression<Long> commentCountOf(QBoard board){
        return JPAExpressions
                .select(comment.count())
                .from(comment)
                .where(comment.board.id.eq(board.id));
    }
}
