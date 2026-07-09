package org.example.boardprac.domain.repository;

import org.example.boardprac.domain.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void setUp(){
        memberRepository.save(Member.builder()
                .userId("hong").password("1234").userName("홍길동").build());
    }

    @Test
    void existsByUserId_존재하면_true(){
        assertThat(memberRepository.existsByUserId("hong")).isTrue();
        assertThat(memberRepository.existsByUserId("noBody")).isFalse();
    }
    @Test
    void findByUserId_있으면_회원(){
        assertThat(memberRepository.findByUserId("hong")).isPresent();
        assertThat(memberRepository.findByUserId("noBody")).isEmpty();
    }
}