package qu4lizz.sni.forum.server.models.responses;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class JwtAuthResponse {
    @NotBlank
    private String token;
}
