package qu4lizz.sni.forum.server.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import qu4lizz.sni.forum.server.models.enums.Role;
import qu4lizz.sni.forum.server.models.enums.Status;

import java.util.Collection;
import java.util.Objects;

@Entity
@Data
@Table(name = "user", schema = "public", catalog = "forum")
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "login_code")
    private String loginCode;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
    @OneToMany(mappedBy = "idUser")
    private Collection<PermissionEntity> permissions;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, username, password, loginCode, role);
    }
}
