package qu4lizz.sni.forum.server.models.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class PendingCommentDTO {
    private Integer id;
    private String content;
    private Instant timestamp;
    private UserCommentDTO user;
    private TopicNameDTO topic;
}
