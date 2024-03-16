package qu4lizz.sni.forum.server.services;

import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import qu4lizz.sni.forum.server.exceptions.ForbiddenException;
import qu4lizz.sni.forum.server.models.dto.*;
import qu4lizz.sni.forum.server.models.entities.CommentEntity;
import qu4lizz.sni.forum.server.models.entities.PermissionEntity;
import qu4lizz.sni.forum.server.models.entities.TopicEntity;
import qu4lizz.sni.forum.server.models.enums.Status;
import qu4lizz.sni.forum.server.models.requests.CommentCreateRequest;
import qu4lizz.sni.forum.server.repositories.CommentRepository;
import qu4lizz.sni.forum.server.repositories.PermissionRepository;
import qu4lizz.sni.forum.server.repositories.TopicRepository;

import java.util.List;

@Service
public class TopicService {
    private final TopicRepository topicRepository;
    private final PermissionRepository permissionRepository;
    private final CommentRepository commentRepository;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final ModelMapper modelMapper;

    public TopicService(TopicRepository topicRepository, PermissionRepository permissionRepository, CommentRepository commentRepository, JwtUserDetailsService jwtUserDetailsService, ModelMapper modelMapper) {
        this.topicRepository = topicRepository;
        this.permissionRepository = permissionRepository;
        this.commentRepository = commentRepository;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.modelMapper = modelMapper;
    }


    public List<TopicDTO> getAll() {
        List<TopicEntity> topicEntityList = topicRepository.findAll();

        return topicEntityList.stream().map(t -> modelMapper.map(t, TopicDTO.class)).toList();
    }

    public TopicDetailsDTO getById(Integer id) throws ChangeSetPersister.NotFoundException {
        TopicEntity topicEntity = topicRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);

        topicEntity.setComments(topicEntity.getComments()
                .stream().filter(c -> c.getStatus().equals(Status.APPROVED))
                .sorted((c1, c2) -> c2.getTimestamp().compareTo(c1.getTimestamp()))
                .limit(20)
                .toList());

        return modelMapper.map(topicEntity, TopicDetailsDTO.class);
    }

    public TopicPermissionsDTO getPermissionsById(Integer idTopic) {
        JwtUser jwtUser = jwtUserDetailsService.getUserFromJwt();
        if (jwtUser == null)
            return null;

        int idUser = jwtUser.getId();

        PermissionEntity permission = permissionRepository.findByIdUserAndIdTopic(idUser, idTopic);

        return modelMapper.map(permission, TopicPermissionsDTO.class);
    }

    public void createComment(CommentCreateRequest request) throws ForbiddenException {
        JwtUser jwtUser = jwtUserDetailsService.getUserFromJwt();
        request.setIdUser(jwtUser.getId());
        request.setApproved(false);

        PermissionEntity permission = permissionRepository.findByIdUserAndIdTopic(jwtUser.getId(), request.getIdTopic());
        if (permission == null || !permission.getWrite())
            throw new ForbiddenException("You don't have permissions to write in this topic");

        CommentEntity commentEntity = modelMapper.map(request, CommentEntity.class);
        commentEntity.setStatus(Status.REQUESTED);
        commentRepository.save(commentEntity);
    }

    public List<TopicWithoutImageDTO> getAllWithoutImage() {
        List<TopicEntity> topicEntityList = topicRepository.findAll();

        return topicEntityList.stream().map(t -> modelMapper.map(t, TopicWithoutImageDTO.class)).toList();
    }
}
