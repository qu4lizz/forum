package qu4lizz.sni.forum.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import qu4lizz.sni.forum.server.models.entities.PermissionEntity;

import java.util.List;

public interface PermissionRepository extends JpaRepository<PermissionEntity, Integer> {
    PermissionEntity findByIdUserAndIdTopic(Integer idUser, Integer idTopic);
}
