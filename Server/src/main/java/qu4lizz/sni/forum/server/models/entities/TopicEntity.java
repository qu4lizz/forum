package qu4lizz.sni.forum.server.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@Table(name = "topic", schema = "public", catalog = "forum")
public class TopicEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "image")
    private byte[] image;
    @OneToMany(mappedBy = "idTopic")
    private List<CommentEntity> commentsById;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TopicEntity that = (TopicEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Arrays.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }
}
