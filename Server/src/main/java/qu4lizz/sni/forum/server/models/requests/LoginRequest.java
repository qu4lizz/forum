package qu4lizz.sni.forum.server.models.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import qu4lizz.sni.forum.server.models.validators.SQLValid;
import qu4lizz.sni.forum.server.models.validators.XSSValid;

@Data
public class LoginRequest {
    @NotBlank
    @SQLValid
    @XSSValid
    private String username;
    @NotBlank
    @SQLValid
    @XSSValid
    private String password;
}
