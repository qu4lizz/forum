package qu4lizz.sni.forum.server.models.dto;

import lombok.Data;

@Data
public class TopicDTO {
    private Integer id;
    private String name;
    private byte[] image;
}
