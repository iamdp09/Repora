package Repora.backend.demo.service;

import Repora.backend.demo.entity.User;
import Repora.backend.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    public final UserRepository userRepository;
    public final TextEncryptor tokenEncryptor;

    @Transactional(readOnly = true)
    public User requiredById(Long githubId) {
        return userRepository.findByGithubId(githubId).orElseThrow(()-> new IllegalArgumentException("User not found with githubId: " + githubId));
    }

    public String decryptAccessToken(User user) {
        return tokenEncryptor.decrypt(user.getAccessToken());
    }

    private static Long toLong(Object value) {
        if (value instanceof Number number) {
            return number.longValue();
        }

        return Long.parseLong(String.valueOf(value));
    }

    // here login is github username
    // we are just upserting the user from github & saving to our database
    @Transactional
    public User upsertFromGithub(Map<String, Object> attributes, String acccessToken, String scopes) {
        Long githubId = toLong(attributes.get("id"));
        String login = String.valueOf(attributes.get("login"));

        String name = attributes.get("name") != null ?
                String.valueOf(attributes.get("name")) : login;

        String avatarUrl = attributes.get("avatar_url") != null ?
                String.valueOf(attributes.get("avatar_url")) : null;

        String encryptedToken = tokenEncryptor.encrypt(acccessToken);

        User user = userRepository.findByGithubId(githubId).orElseGet(User::new);
            user.setGithubId(githubId);
            user.setGithubUsername(login);
            user.setDisplayName(name);
            user.setAvatarUrl(avatarUrl);
            user.setAccessToken(encryptedToken);
            user.setTokenScopes(scopes);
            return userRepository.save(user);
        }
}
