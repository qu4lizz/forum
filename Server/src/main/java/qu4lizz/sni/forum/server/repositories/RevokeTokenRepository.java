package qu4lizz.sni.forum.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import qu4lizz.sni.forum.server.models.entities.RevokedTokenEntity;

public interface RevokeTokenRepository extends JpaRepository<RevokedTokenEntity, String> {
}
