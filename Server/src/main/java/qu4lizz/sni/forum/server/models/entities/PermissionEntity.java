package qu4lizz.sni.forum.server.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@Table(name = "permission", schema = "public", catalog = "forum")
public class PermissionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "update")
    private Boolean update;
    @Basic
    @Column(name = "write")
    private Boolean write;
    @Basic
    @Column(name = "delete")
    private Boolean delete;
    @Basic
    @Column(name = "id_user")
    private Integer idUser;
    @Basic
    @Column(name = "id_topic")
    private Integer idTopic;

    public PermissionEntity(int idU, int idT) {
        idTopic = idT;
        idUser = idU;
        write = true;
        delete = false;
        update = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PermissionEntity that = (PermissionEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(update, that.update) && Objects.equals(write, that.write) && Objects.equals(delete, that.delete) && Objects.equals(idUser, that.idUser) && Objects.equals(idTopic, that.idTopic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, update, write, delete, idUser, idTopic);
    }
}
