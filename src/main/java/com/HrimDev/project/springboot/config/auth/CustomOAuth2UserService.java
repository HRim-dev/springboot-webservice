package com.HrimDev.project.springboot.config.auth;

import com.HrimDev.project.springboot.config.auth.dto.OAuthAttributes;
import com.HrimDev.project.springboot.config.auth.dto.SessionUser;
import com.HrimDev.project.springboot.domain.user.User;
import com.HrimDev.project.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;
    
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User OAuth2User=delegate.loadUser(userRequest);

        //현재 로그인 진행중인 서비스를 구분(네이버인지 구글인지)
        String registrationId=userRequest.getClientRegistration().getRegistrationId();
        
        //OAuth2 로그인 진행시 키가되는 필드값(Primary Key)
        String userNameAttributeName=userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        
        //OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담는 클래스
        OAuthAttributes attributes=OAuthAttributes.of(registrationId, userNameAttributeName, OAuth2User.getAttributes());
        
        User user=saveOrUpdate(attributes);
        
        httpSession.setAttribute("user",new SessionUser(user));
        
        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                                       attributes.getAttributes(),
                                       attributes.getNameAttributeKey());
    }
    
    private User saveOrUpdate(OAuthAttributes attributes){
        User user=userRepository.findByEmail(attributes.getEmail())
                                .map(entity->entity.update(attributes.getName(),attributes.getPicture()))
                                .orElse(attributes.toEntity());
        
        return userRepository.save(user);
    }
}
