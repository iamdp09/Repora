package Repora.backend.demo.service.ai;

import Repora.backend.demo.dto.CitationDto;

import java.util.List;

public record RetrievedContext(
        List<CitationDto> citations,
        String contextText) {
}
