package qu4lizz.sni.forum.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import qu4lizz.sni.forum.server.models.entities.TopicEntity;

public interface TopicRepository extends JpaRepository<TopicEntity, Integer> {
}
