package org.example.token.service;

import org.example.token.domain.entity.User;
import org.example.token.domain.repository.UserRepository;
import org.example.token.dto.SignInRequestDto;
import org.example.token.dto.SignInResponseDto;
import org.example.token.dto.SignUpRequestDto;
import org.example.token.exception.DuplicateUserIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public void join(SignUpRequestDto request){
        if(userRepository.existsByUserId(request.getUserId())){
           throw new DuplicateUserIdException("[회원가입] 이미 사용중인 아이디입니다.");
        }
        User user = request.toUser(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }

    public SignInResponseDto login(SignInRequestDto request){
        //form-login에서는 필터가 하던 아이디/비밀번호 검증을 직접 호출한다
        //실패하면 AuthenticationException이 던져진다.
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUserId(),request.getPassword())
        );

        User user = authenticate.getPrincipal().getUser();
    }
}
