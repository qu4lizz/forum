package qu4lizz.sni.forum.server.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    // id used in combination with code
    private Integer tempId;
}
