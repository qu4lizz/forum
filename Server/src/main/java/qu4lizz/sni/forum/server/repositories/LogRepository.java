package qu4lizz.sni.forum.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import qu4lizz.sni.forum.server.models.entities.LogEntity;

public interface LogRepository extends JpaRepository<LogEntity, Integer> {
}
