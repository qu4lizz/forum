package qu4lizz.sni.forum.server.models.requests;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class LoginCodeRequest {
    @NotBlank
    private String code;
}
