package org.example.basicboard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.basicboard.domain.entity.Member;
import org.example.basicboard.domain.repository.MemberRepository;
import org.example.basicboard.dto.LoginRequestDto;
import org.example.basicboard.dto.MemberJoinRequestDto;
import org.example.basicboard.exception.DuplicateUserIdException;
import org.example.basicboard.mapper.MemberMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
//이 클래스의 "모든 메서드"에 기본 적용
//- readOnly = true의 효과
//"이 트랜잭션은 데이터를 변경하지 안흔ㄴ다" 라고 JPA에 선언 -> 최적화
//하이버네이트가 변경감지를 위한 스냅샷을 만들지 않음 -> 메모리/성능에 유리
//Insert/Update/Delete가 필요한 메서드는 메서드에 @Transactional을 다시 붙여 덮어 쓴다

//* @Slf4j로 서비스 레벨 로깅하기

// lombok을 안쓰면 ...
// private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MemberService.class);

//로그 레벨 5단계 - "이 메시지가 얼마나 중요한가"의 등급
//trace < debug < info < warn < error
//-trace/debug : 개발 중 흐름 추적용(운영 기본 설정에선 안 지킴)
//-info : 의미 있는 비즈니스 이벤트(가입 완료, 글 등록 등 "정상인데 기록할 가치가 있는 일")
//-warn : 이상하지만 서비스는 계속되는 상황(로그인 실패, 중복 가입 시도 등)
//-error : 예상 못한 실패(주로 500 계열)

// {} 플레이스홀더 - "문자열 + 값" 대신 반드시 이것을 사용함
// log.debug("가입 요청 : "+dto.getUserId()) (X), 해당 로그 레벨이 꺼져 있어도 문자열 연결 비용이 발생
// log.debug("가입 요청 : userId={}",dto.getUserId()) (O), 실제로 해당 레벨 로그가 찍힐 때만 조립(지연 평가)

//민감한 정보를 로그에 남기지 않는다.
//- 객체를 통째로 찍으면, 그 안의 값들이 그대로 로그에 남음
//- 로그 파일은 오래 보관되고 여러 사람이 볼 수 있으므로, 비밀번호/토큰/주민번호 등은 로그에서 반드시 제외해야함

//AOP 로깅과의 역할 분담
//- AOP : "무엇이 호출됐고, 몇 ms 걸렸나" (기계적/공통사항 - 모든 요청에 일괄 적용)
//- 서비스 로그 : "비즈니스에 무슨 일이 있었나" (선별적/의미 - 분기점, 상태 변화, 실패 지점만)
//- 그래서 서비스에서는 "메서드 시작/끝" 로그를 찍지 않는다.
@Slf4j
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Transactional
    public void join(MemberJoinRequestDto dto){
        //아이디 중복 체크
        if(memberRepository.existsByUserId(dto.getUserId())){
            //실패지만 "예상 범위 안의 실패" : warn
            log.warn("회원가입 실패(아이디 중복): userId={}",dto.getUserId());

            //예외 공통화
            throw new DuplicateUserIdException("[회원가입] 이미 존재하는 아이디입니다.");
        }
        memberRepository.save(memberMapper.toEntity(dto));
        //성공
        log.info("회원가입 성공: userId={}, userName={}",dto.getUserId(),dto.getUserName());
    }

    public Optional<Member> login(LoginRequestDto dto){
        // return 한 줄에 쓰인 람다식과 Optional.filter 이해하기
        // ---------------------------------------------------------------------------------
        // # findByUserId 는 Optional<Member> 를 돌려준다 (회원이 있을 수도, 없을 수도)
        // # .filter( member -> 조건 ) 의 동작:
        //     - Optional 안에 값이 "있고" + 람다가 true  -> 그 값을 그대로 유지
        //     - 값이 "없거나"          + 람다가 false -> 빈 Optional(Optional.empty)로 만든다
        //   => "아이디로 찾은 회원이 있고, 비밀번호까지 일치하면 남기고, 아니면 비운다" = 로그인 성공/실패 판정

        // # member -> member.getPassword().equals(...) 가 바로 람다식(이름 없는 함수)이다
        //     member                         : 입력 파라미터 (Optional 안에 든 Member)
        //     ->                             : "이것을 받아서 ~를 반환한다"
        //     member.getPassword().equals(..): 반환값 (boolean). 비밀번호가 같으면 true

        // # 만약 람다(와 Optional)를 쓰지 않았다면, 아래처럼 풀어 쓴 것과 같다:
        //
        //     // 1) 아이디로 회원을 조회한다 (없으면 null 이 나오도록 orElse(null) 사용)
        //     Member member = memberRepository.findByUserId(request.getUsername()).orElse(null);
        //
        //     // 2) 회원이 존재하고(null 아님) + 비밀번호가 일치하면 -> 로그인 성공
        //     if (member != null && member.getPassword().equals(request.getPassword())) {
        //         return Optional.of(member);   // 성공: 회원을 담아 반환
        //     }
        //
        //     // 3) 아이디가 없거나 비밀번호가 틀리면 -> 로그인 실패
        //     return Optional.empty();          // 실패: 빈 Optional 반환
        //
        //   => 위 if 분기(널 체크 + 비밀번호 비교)를 .filter(람다) 한 줄로 압축한 것이 아래 코드다

        Optional<Member> result =  memberRepository.findByUserId(dto.getUsername())
                .filter(
                        member -> member.getPassword().equals(dto.getPassword())
                );
        if(result.isEmpty()){
            log.warn("로그인 실패 : userName={}",dto.getUsername());
        }else{
            log.info("로그인 성공 : userName={}",dto.getUsername());
        }
        return result;
    }
}
