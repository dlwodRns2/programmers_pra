package org.example.securityprac.service;

import lombok.RequiredArgsConstructor;
import org.example.securityprac.dto.SignUpRequestDto;
import org.example.securityprac.entity.domain.User;
import org.example.securityprac.entity.repository.UserRepository;
import org.example.securityprac.exception.DuplicateUserIdException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public void signUp(SignUpRequestDto request){
        if(userRepository.existsByUserId(request.getUserId())){
            throw new DuplicateUserIdException(HttpStatus.CONFLICT,"[회원가입] 이미 사용 중인 아이디입니다.");
        }
        User user = request.toUser(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }
}
