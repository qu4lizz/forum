package qu4lizz.sni.forum.server.models.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import qu4lizz.sni.forum.server.models.validators.SQLValid;
import qu4lizz.sni.forum.server.models.validators.XSSValid;

@Data
public class LoginCodeRequest {
    @NotNull
    private Integer id;
    @NotBlank
    @SQLValid
    @XSSValid
    private String code;
}
