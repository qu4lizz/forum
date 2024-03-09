package qu4lizz.sni.forum.server.models.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentUpdateContentRequest {
    @NotNull
    private String content;
}
