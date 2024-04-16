package qu4lizz.sni.forum.server.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@Table(name = "revoked_token", schema = "public", catalog = "forum")
public class RevokedTokenEntity {
    @Id
    @Column(name = "value")
    private String value;

    public RevokedTokenEntity(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RevokedTokenEntity that = (RevokedTokenEntity) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
