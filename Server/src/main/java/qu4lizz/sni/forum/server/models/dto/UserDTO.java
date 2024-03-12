package qu4lizz.sni.forum.server.models.dto;

import lombok.Data;
import qu4lizz.sni.forum.server.models.enums.Role;
import qu4lizz.sni.forum.server.models.enums.Status;

import java.util.List;

@Data
public class UserDTO {
    private Integer id;
    private String email;
    private String username;
    private Role role;
    private Status status;
    private List<PermissionDTO> permissions;
}
