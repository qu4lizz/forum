package qu4lizz.sni.forum.server.models.dto;

import lombok.Data;

@Data
public class TopicPermissionsDTO {
    private Boolean update;
    private Boolean write;
    private Boolean delete;
}
