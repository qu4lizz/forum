package qu4lizz.sni.forum.server.models.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import qu4lizz.sni.forum.server.models.enums.Status;

@Data
public class CommentUpdateStatusRequest {
    @NotNull
    private Status status;
}
