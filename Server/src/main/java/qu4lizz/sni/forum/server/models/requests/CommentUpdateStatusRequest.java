package qu4lizz.sni.forum.server.models.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import qu4lizz.sni.forum.server.models.enums.Status;

@Data
public class CommentUpdateStatusRequest {
    @NotNull
    @Pattern(regexp = "^(REQUESTED|APPROVED|REJECTED)$")
    private Status status;
}
