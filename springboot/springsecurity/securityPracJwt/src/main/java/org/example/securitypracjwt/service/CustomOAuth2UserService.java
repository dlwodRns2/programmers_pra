package org.example.securitypracjwt.service;

import lombok.RequiredArgsConstructor;
import org.example.securitypracjwt.config.oauth2.AuthProvider;
import org.example.securitypracjwt.config.oauth2.CustomOAuth2User;
import org.example.securitypracjwt.config.oauth2.OAuth2UserInfo;
import org.example.securitypracjwt.config.oauth2.OAuth2UserInfoFactory;
import org.example.securitypracjwt.domain.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        String nameAttributeKey = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        AuthProvider provider = AuthProvider.from(registrationId);
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.of(provider, oAuth2User.getAttributes());

        if(userInfo.email()==null){
            throw new OAuth2AuthenticationException(
                    new OAuth2Error("Email is required"),
                    "SNS 계정에서 이메일을 가져오지 못했습니다. 이메일 제공 동의가 필요합니다."
            );
        }

        return userRepository.findByProviderAndProviderId(provider,userInfo.id())
                .map(
                        existing  -> {
                            existing.updateProfile(userInfo.name());

                            return new CustomOAuth2User(
                                    provider,
                                    userInfo,
                                    existing,
                                    oAuth2User.getAttributes(),
                                    nameAttributeKey
                            );
                        }
                ).orElseGet(
                        ()->CustomOAuth2User.unregistered(
                                provider,
                                userInfo,
                                oAuth2User.getAttributes(),
                                nameAttributeKey
                        )
                );
    }
}
