package qu4lizz.sni.forum.server.models.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import qu4lizz.sni.forum.server.models.validators.SQLValid;
import qu4lizz.sni.forum.server.models.validators.XSSValid;

@Data
public class LoginRequest {
    @NotBlank
    @SQLValid
    @XSSValid
    @Size(max = 50)
    private String username;
    @NotBlank
    @SQLValid
    @XSSValid
    @Size(max = 50)
    private String password;
}
