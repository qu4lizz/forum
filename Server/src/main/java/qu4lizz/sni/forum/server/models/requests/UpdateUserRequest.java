package qu4lizz.sni.forum.server.models.requests;

import lombok.Data;
import lombok.ToString;
import qu4lizz.sni.forum.server.models.dto.PermissionDTO;
import qu4lizz.sni.forum.server.models.enums.Role;
import qu4lizz.sni.forum.server.models.enums.Status;

import java.util.List;

@Data
@ToString
public class UpdateUserRequest {
    private Role role;
    private Status status;
    private List<PermissionDTO> permissions;
}
