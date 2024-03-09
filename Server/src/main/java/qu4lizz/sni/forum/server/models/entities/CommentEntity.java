package qu4lizz.sni.forum.server.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import qu4lizz.sni.forum.server.models.enums.Status;

import java.time.Instant;
import java.util.Objects;

@Entity
@Data
@Table(name = "comment", schema = "public", catalog = "forum")
public class CommentEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "content")
    private String content;
    @Basic
    @Column(name = "timestamp")
    private Instant timestamp;
    @Basic
    @Column(name = "id_topic")
    private Integer idTopic;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private UserEntity user;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentEntity that = (CommentEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, timestamp, idTopic);
    }
}
