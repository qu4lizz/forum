package qu4lizz.sni.forum.server.models.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import qu4lizz.sni.forum.server.models.validators.SQLValid;
import qu4lizz.sni.forum.server.models.validators.XSSValid;

import java.time.Instant;

@Data
public class CommentCreateRequest {
    @NotBlank
    @SQLValid
    @XSSValid
    @Size(max = 1000)
    private String content;
    @NotNull
    private Instant timestamp;
    @NotNull
    private Integer idTopic;
    private Integer idUser;
    private Boolean approved;
}
