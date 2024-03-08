package qu4lizz.sni.forum.server.models.dto;

import lombok.Data;

import java.util.List;

@Data
public class TopicDetailsDTO {
    private Integer id;
    private String name;
    private byte[] image;
    private List<CommentDTO> comments;
}
