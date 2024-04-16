package qu4lizz.sni.forum.server.models.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import qu4lizz.sni.forum.server.models.validators.SQLValid;
import qu4lizz.sni.forum.server.models.validators.XSSValid;

@Data
public class CommentUpdateContentRequest {
    @NotNull
    @SQLValid
    @XSSValid
    @Size(max = 1000)
    private String content;
}
