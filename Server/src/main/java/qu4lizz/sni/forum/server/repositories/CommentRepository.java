package qu4lizz.sni.forum.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import qu4lizz.sni.forum.server.models.entities.CommentEntity;
import qu4lizz.sni.forum.server.models.enums.Status;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    List<CommentEntity> findAllByStatusOrderByTimestamp(Status status);
}
