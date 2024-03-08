package qu4lizz.sni.forum.server.services;

import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import qu4lizz.sni.forum.server.models.dto.TopicDTO;
import qu4lizz.sni.forum.server.models.dto.TopicDetailsDTO;
import qu4lizz.sni.forum.server.models.entities.TopicEntity;
import qu4lizz.sni.forum.server.repositories.TopicRepository;

import java.util.List;

@Service
public class TopicService {
    private final TopicRepository topicRepository;
    private final ModelMapper modelMapper;

    public TopicService(TopicRepository topicRepository, ModelMapper modelMapper) {
        this.topicRepository = topicRepository;
        this.modelMapper = modelMapper;
    }


    public List<TopicDTO> getAll() {
        List<TopicEntity> topicEntityList = topicRepository.findAll();

        return topicEntityList.stream().map(t -> modelMapper.map(t, TopicDTO.class)).toList();
    }

    public TopicDetailsDTO getById(Integer id) throws ChangeSetPersister.NotFoundException {
        TopicEntity topicEntity = topicRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);

        topicEntity.setComments(topicEntity.getComments().stream().limit(20).toList());

        return modelMapper.map(topicEntity, TopicDetailsDTO.class);
    }
}
