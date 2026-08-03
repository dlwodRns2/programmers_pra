package org.example.oauth2.service;

import lombok.RequiredArgsConstructor;
import org.example.oauth2.config.security.CustomUserDetails;
import org.example.oauth2.domain.entity.User;
import org.example.oauth2.domain.repository.UserRepository;
import org.example.oauth2.dto.SignInRequestDto;
import org.example.oauth2.dto.SignInResponseDto;
import org.example.oauth2.dto.SignUpRequestDto;
import org.example.oauth2.exception.DuplicateUserIdException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Transactional
    public void signUp(SignUpRequestDto request){
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

        User user = ((CustomUserDetails)authenticate.getPrincipal()).getUser();
        TokenService.TokenPair tokenPair = tokenService.issueToken(user);

        return SignInResponseDto.builder()
                .isLoggedIn(true)
                .message("로그인 성공")
                .url("/")
                .accessToken(tokenPair.accessToken())
                .refreshToken(tokenPair.refreshToken())
                .userName(user.getName())
                .userId(user.getUserId())
                .build();
    }


}
