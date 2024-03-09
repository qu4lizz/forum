package qu4lizz.sni.forum.server.services;

import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import qu4lizz.sni.forum.server.exceptions.ForbiddenException;
import qu4lizz.sni.forum.server.models.dto.JwtUser;
import qu4lizz.sni.forum.server.models.dto.PendingCommentDTO;
import qu4lizz.sni.forum.server.models.dto.TopicNameDTO;
import qu4lizz.sni.forum.server.models.entities.CommentEntity;
import qu4lizz.sni.forum.server.models.entities.PermissionEntity;
import qu4lizz.sni.forum.server.models.entities.TopicEntity;
import qu4lizz.sni.forum.server.models.enums.Status;
import qu4lizz.sni.forum.server.models.requests.CommentUpdateContentRequest;
import qu4lizz.sni.forum.server.models.requests.CommentUpdateStatusRequest;
import qu4lizz.sni.forum.server.repositories.CommentRepository;
import qu4lizz.sni.forum.server.repositories.PermissionRepository;
import qu4lizz.sni.forum.server.repositories.TopicRepository;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PermissionRepository permissionRepository;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final TopicRepository topicRepository;
    private final ModelMapper modelMapper;

    public CommentService(CommentRepository commentRepository, PermissionRepository permissionRepository, JwtUserDetailsService jwtUserDetailsService, TopicRepository topicRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.permissionRepository = permissionRepository;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.topicRepository = topicRepository;
        this.modelMapper = modelMapper;
    }

    public List<PendingCommentDTO> getAllPendingComments() {
        List<CommentEntity> commentEntityList = commentRepository.findAllByStatusOrderByTimestamp(Status.REQUESTED);

        return commentEntityList.stream()
                .map(commentEntity -> {
                    PendingCommentDTO pendingCommentDTO = modelMapper.map(commentEntity, PendingCommentDTO.class);
                    TopicEntity topicEntity = topicRepository.findById(commentEntity.getIdTopic()).orElse(null);
                    if (topicEntity != null) {
                        TopicNameDTO topicNameDTO = modelMapper.map(topicEntity, TopicNameDTO.class);
                        pendingCommentDTO.setTopic(topicNameDTO);
                    }
                    return pendingCommentDTO;
                })
                .toList();
    }

    public void updateCommentStatus(Integer id, CommentUpdateStatusRequest request)
            throws ChangeSetPersister.NotFoundException, ForbiddenException {
        CommentEntity commentEntity = commentRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        JwtUser jwtUser = jwtUserDetailsService.getUserFromJwt();

        PermissionEntity permission = permissionRepository.findByIdUserAndIdTopic(jwtUser.getId(), commentEntity.getIdTopic());

        if ((request.getStatus().equals(Status.APPROVED) && !permission.getWrite()) ||
                (request.getStatus().equals(Status.REJECTED) && !permission.getDelete())) {
            throw new ForbiddenException("You don't have permission to use this operation on this topic");
        }

        commentEntity.setStatus(request.getStatus());

        commentRepository.save(commentEntity);
    }

    public void updateCommentContent(Integer id, CommentUpdateContentRequest request) throws ChangeSetPersister.NotFoundException, ForbiddenException {
        CommentEntity commentEntity = commentRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        JwtUser jwtUser = jwtUserDetailsService.getUserFromJwt();

        PermissionEntity permission = permissionRepository.findByIdUserAndIdTopic(jwtUser.getId(), commentEntity.getIdTopic());

        if (!permission.getUpdate()) {
            throw new ForbiddenException("You don't have permission to use this operation on this topic");
        }

        commentEntity.setContent(request.getContent());

        commentRepository.save(commentEntity);
    }
}
