package qu4lizz.sni.forum.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import qu4lizz.sni.forum.server.models.entities.PermissionEntity;

public interface PermissionRepository extends JpaRepository<PermissionEntity, Integer> {
}
