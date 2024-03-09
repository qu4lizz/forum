package qu4lizz.sni.forum.server.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import qu4lizz.sni.forum.server.models.enums.Role;

@Data
@AllArgsConstructor
public class JwtAuthResponse {
    private String token;
    private String username;
    private Role role;
}
