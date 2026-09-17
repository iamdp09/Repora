package Repora.backend.demo.security;

import Repora.backend.demo.entity.User;
import Repora.backend.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GithubOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserService userService;
    private final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

    @Nullable
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User githubUser = delegate.loadUser(userRequest);
        String acccessToken = userRequest.getAccessToken().getTokenValue();
        String scopes= userRequest.getAccessToken().getScopes() !=null ?
        String.join(",", userRequest.getAccessToken().getScopes()) : "read:user,repo";

        User user= userService.upsertFromGithub(githubUser.getAttributes(),acccessToken,scopes);
        return new AppUserPrincipal(user, githubUser.getAttributes());
    }
}
