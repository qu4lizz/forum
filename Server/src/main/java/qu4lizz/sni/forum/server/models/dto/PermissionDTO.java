package qu4lizz.sni.forum.server.models.dto;

import lombok.Data;

@Data
public class PermissionDTO {
    private Integer id;
    private Boolean update;
    private Boolean write;
    private Boolean delete;
    private Integer idUser;
    private Integer idTopic;
}
