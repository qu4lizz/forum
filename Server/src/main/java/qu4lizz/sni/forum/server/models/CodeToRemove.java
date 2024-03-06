package qu4lizz.sni.forum.server.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeToRemove {
    private String code;
    private Instant timestamp;
    private Integer userId;
}
