package org.example.authservice.config.security;

import lombok.Builder;
import lombok.Getter;
import org.example.authservice.domain.entity.User;
import org.example.authservice.domain.entity.UserStatus;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Builder
public class CustomUserDetails implements UserDetails {

    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority( user.getRole().name() )
        );
    }

    @Override
    public @Nullable String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserId();
    }

    // 탈퇴(WITHDRAWING/WITHDRAWN) 계정은 비활성으로 취급
    // -> DaoAuthenticationProvider가 비밀번호 검사 전에 DisabledException을 던져 로그인이 막힌다.
    @Override
    public boolean isEnabled() {
        return user.getStatus() == UserStatus.ACTIVE;
    }
}
