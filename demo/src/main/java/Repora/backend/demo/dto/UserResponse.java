package Repora.backend.demo.dto;

import java.util.UUID;

public record UserResponse(
        UUID id,
        Long githubId,
        String githubUsername,
        String displayName,
        String avatarUrl
) {


}