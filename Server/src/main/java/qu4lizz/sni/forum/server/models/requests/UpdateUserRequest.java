package qu4lizz.sni.forum.server.models.requests;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.ToString;
import qu4lizz.sni.forum.server.models.dto.PermissionDTO;
import qu4lizz.sni.forum.server.models.enums.Role;
import qu4lizz.sni.forum.server.models.enums.Status;

import java.util.List;

@Data
@ToString
public class UpdateUserRequest {
    @Pattern(regexp = "^(USER|MODERATOR|ADMIN)$")
    private Role role;
    @Pattern(regexp = "^(REQUESTED|APPROVED|REJECTED)$")
    private Status status;
    private List<PermissionDTO> permissions;
}
