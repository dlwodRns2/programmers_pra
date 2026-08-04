package org.example.securitypracjwt.service;

import io.jsonwebtoken.security.Password;
import lombok.RequiredArgsConstructor;
import org.example.securitypracjwt.config.security.CustomUserDetails;
import org.example.securitypracjwt.domain.entity.Role;
import org.example.securitypracjwt.domain.entity.User;
import org.example.securitypracjwt.domain.repository.UserRepository;
import org.example.securitypracjwt.dto.OAuthSignupRequestDto;
import org.example.securitypracjwt.dto.SignInResponseDto;
import org.example.securitypracjwt.dto.SignUpRequestDto;
import org.example.securitypracjwt.dto.SignupPayloadDto;
import org.example.securitypracjwt.exception.DuplicateUserIdException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.example.securitypracjwt.domain.entity.Role.ROLE_USER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public SignInResponseDto signIn(String userName, String password){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userName,password)
        );

        User user = ((CustomUserDetails)authentication.getPrincipal()).getUser();

        // 인증 성공의 결과가 "세션 저장"이 아니라 "토큰 발급" — form-login과의 결정적 차이
        TokenService.TokenPair tokens = tokenService.issueTokens(user);

        return SignInResponseDto.builder()
                .isLoggedIn(true).message("로그인 성공")
                .url("/")
                .accessToken(tokens.accessToken())
                .refreshToken(tokens.refreshToken())
                .userId(user.getUserId())
                .userName(user.getName())
                .build();
    }
    @Transactional
    public void signUp(SignUpRequestDto request){
        if(userRepository.existsByUserId(request.getUserId())){
            throw new DuplicateUserIdException("[회원가입] 이미 사용중인 아이디입니다.");
        }

        User user = request.toUser(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
    }
    @Transactional
    public SignInResponseDto oauthSignUp(OAuthSignupRequestDto request){
        SignupPayloadDto payload = tokenService.getSignupPayload(request.getSignupToken());
        Role role = request.getRole();

        User user = userRepository.findByProviderAndProviderId(payload.getProvider(), payload.getProviderId())
                .orElseGet(()->userRepository.save(
                        User.builder()
                                .userId(payload.getProvider().name().toLowerCase()+"_"+payload.getProviderId())
                                .name(payload.getName())
                                .email(payload.getEmail())
                                .provider(payload.getProvider())
                                .providerId(payload.getProviderId())
                                .role(role!=null ? role : ROLE_USER)
                                .build()
                ));
        TokenService.TokenPair tokenPair = tokenService.issueTokens(user);
        return SignInResponseDto.builder()
                .isLoggedIn(true)
                .message("가입이 완료되었습니다")
                .url("/")
                .build();
    }
}
