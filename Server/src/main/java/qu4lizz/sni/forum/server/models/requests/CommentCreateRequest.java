package qu4lizz.sni.forum.server.models.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.Instant;

@Data
public class CommentCreateRequest {
    @NotBlank
    private String content;
    @NotNull
    private Instant timestamp;
    @NotNull
    private Integer idTopic;
    private Integer idUser;
    private Boolean approved;
}
