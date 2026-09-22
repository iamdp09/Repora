package Repora.backend.demo.service.indexing;

import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.EncodingType;
import com.knuddels.jtokkit.api.IntArrayList;
import org.springframework.ai.transformer.splitter.TextSplitter;

import java.util.ArrayList;
import java.util.List;

public class OverlappingTokenTextSplitter extends TextSplitter {

    private final Encoding encoding;
    private final int chunkTokens;
    private final int overlapTokens;

    public OverlappingTokenTextSplitter(int chunkTokens, int overlapTokens) {
        EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();

        this.encoding =
                registry.getEncoding(EncodingType.CL100K_BASE);

        this.chunkTokens = chunkTokens;
        this.overlapTokens = overlapTokens;
    }

    @Override
    protected List<String> splitText(String text) {

        List<Integer> tokens = encoding.encode(text).boxed();

        List<String> chunks = new ArrayList<>();

        int step = chunkTokens - overlapTokens;

        if (step <= 0) {
            throw new IllegalArgumentException(
                    "overlapTokens must be smaller than chunkTokens"
            );
        }

        for (int start = 0; start < tokens.size(); start += step) {

            int end = Math.min(
                    start + chunkTokens,
                    tokens.size()
            );

            List<Integer> window =
                    tokens.subList(start, end);

            chunks.add(decodeTokens(window));

            if (end == tokens.size()) {
                break;
            }
        }

        return chunks;
    }

    private String decodeTokens(List<Integer> tokens) {

        IntArrayList tokenList = new IntArrayList();

        for (Integer token : tokens) {
            tokenList.add(token);
        }

        return encoding.decode(tokenList);
    }
}