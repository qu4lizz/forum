package qu4lizz.sni.forum.server.models.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class LoginCodeRequest {
    @NotNull
    private Integer id;
    @NotBlank
    private String code;
}
