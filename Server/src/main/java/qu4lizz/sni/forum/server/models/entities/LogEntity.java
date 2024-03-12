package qu4lizz.sni.forum.server.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.Objects;

@Entity
@Data
@Table(name = "log", schema = "public", catalog = "forum")
public class LogEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "timestamp")
    private Instant timestamp;
    @Basic
    @Column(name = "content")
    private String content;

    public LogEntity() {}

    public LogEntity(String content) {
        id = null;
        timestamp = Instant.now();
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogEntity logEntity = (LogEntity) o;
        return Objects.equals(id, logEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timestamp, content);
    }
}
