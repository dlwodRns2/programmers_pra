package org.example.boardprac.service;

import lombok.RequiredArgsConstructor;
import org.example.boardprac.config.security.CustomUserDetails;
import org.example.boardprac.domain.entity.Member;
import org.example.boardprac.domain.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;
    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUserId(username)
                .orElseThrow(()->new UsernameNotFoundException(username + " not found"));

        return CustomUserDetails.builder()
                .member(member)
                .build();
    }
}
